/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettingssample

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.view.WindowCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.Preference
import com.chillibits.composenumberpicker.HorizontalNumberPicker
import com.chillibits.simplesettings.clicklistener.DialogClickListener
import com.chillibits.simplesettings.clicklistener.LibsClickListener
import com.chillibits.simplesettings.clicklistener.PlayStoreClickListener
import com.chillibits.simplesettings.clicklistener.WebsiteClickListener
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.core.SimpleSettingsConfig
import com.chillibits.simplesettings.item.SimpleSwitchPreference
import com.chillibits.simplesettings.tool.*

class MainActivity : AppCompatActivity(), SimpleSettingsConfig.OptionsItemSelectedCallback,
    SimpleSettingsConfig.PreferenceCallback, DialogClickListener.DialogResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        val inputPref = getPreferenceLiveData(this, "inputpreference", "Default")

        setContent {
            AppTheme {
                MainView(inputPref = inputPref)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_github -> openGitHubPage()
        }
        return super.onOptionsItemSelected(item)
    }

    @Preview(name = "MainView", showSystemUi = true)
    @Composable
    private fun MainView(inputPref: LiveData<String> = MutableLiveData()) {
        val inputPrefState by inputPref.observeAsState()
        val numberSwitchPreferences = remember { mutableStateOf(3) }

        ConstraintLayout(
            constraintSet = ConstraintSet {
                val layout = createRefFor("layout")
                constrain(layout) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.layoutId("layout")) {
                val componentModifiers = Modifier
                    .align(CenterHorizontally)
                    .padding(6.dp)

                Row(modifier = componentModifiers) {
                    Text(stringResource(R.string.number_of_switch_preferences))
                }
                Row(modifier = componentModifiers) {
                    HorizontalNumberPicker(
                        min = 1,
                        max = 10,
                        default = 3,
                        modifier = Modifier.padding(10.dp),
                        onValueChange = { numberSwitchPreferences.value = it }
                    )
                }
                Button(
                    onClick = { openSettingsCodeConfig(numberSwitchPreferences.value) },
                    modifier = componentModifiers,
                ) {
                    Text(
                        text = stringResource(R.string.open_settings_code),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                Button(
                    onClick = { openSettingsXmlConfig() },
                    modifier = componentModifiers
                ) {
                    Text(
                        text = stringResource(R.string.open_settings_xml),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                Button(
                    onClick = { openSettingsPaged() },
                    modifier = componentModifiers
                ) {
                    Text(
                        text = stringResource(R.string.open_settings_paged),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
                Spacer(Modifier.height(50.dp))
                Text(
                    text = stringResource(R.string.value_input_preference_, inputPrefState.orEmpty()),
                    modifier = componentModifiers,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }

    private fun openSettingsCodeConfig(numberOfSwitchPreferences: Int) {
        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .setOptionsMenu(R.menu.menu_settings, this)
            .showResetOption(true)
            .setIconSpaceReservedByDefault(false)
            .setPendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .build()

        // Programmatic settings data (especially useful for generating settings options at runtime)
        SimpleSettings(this, config).show {
            Section {
                titleRes = R.string.app_name
                for (i in 1..numberOfSwitchPreferences) {
                    SwitchPref {
                        title = "SwitchPreference $i"
                        summaryOn = "Click to switch - On"
                        summaryOff = "Click to switch - Off"
                        defaultValue = if(i % 2 == 0) SimpleSwitchPreference.ON else SimpleSwitchPreference.OFF
                    }
                }
                TextPref {
                    title = "TextPreference"
                    summary = "Click to open GH page"
                    onClick = WebsiteClickListener(this@MainActivity, getString(R.string.github_link))
                    dependency = "SwitchPreference 1".toCamelCase()
                }
            }
            Section {
                InputPref {
                    title = "InputPreference"
                    summary = "Click to set text"
                    defaultValue = "Default text"
                }
                TextPref {
                    title = "PlayStoreClickListener"
                    summary = "Click here to open PlayStore site of this app"
                    onClick = PlayStoreClickListener(this@MainActivity)
                }
                ListPref {
                    title = "ListPreference"
                    simpleSummaryProvider = true
                    entries = listOf("Apple", "Banana", "Avocado", "Pineapple")
                    defaultIndex = 2
                }
                MSListPref {
                    title = "MSListPreference"
                    simpleSummaryProvider = true
                    entries = listOf("Apple", "Banana", "Avocado", "Pineapple")
                }
                TextPref {
                    title = "LibsClickListener"
                    onClick = LibsClickListener(this@MainActivity)
                }
                LibsPref {
                    activityTitle = "LibsPreference"
                    edgeToEdge = true
                }
                CheckboxPref {
                    title = "CheckboxPreference"
                    summaryOn = "On"
                    summaryOff = "Off"
                }
                DropDownPref {
                    title = "DropDownPreference"
                    simpleSummaryProvider = true
                    entries = listOf("Apple", "Banana", "Avocado", "Pineapple")
                }
                SeekBarPref {
                    title = "SeekBarPreference"
                    summary = "Summary"
                    min = 1
                    max = 30
                    defaultValue = 22
                    showValue = true
                }
                TextPref {
                    title = "Dialog"
                    summary = "Tap to show dialog"
                    onClick = DialogClickListener(this@MainActivity) {
                        title = "Test"
                        message = "This is a test"
                        icon = R.drawable.settings
                        cancelable = false
                        type = DialogClickListener.Type.YES_NO
                    }
                }
            }
        }
        /*
        Apply custom activity transitions while opening. Custom activity transitions while closing
        can be specified by using the setPendingTransition() method on the config object
        */
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun openSettingsXmlConfig() {
        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .setOptionsMenu(R.menu.menu_settings, this)
            .setPreferenceCallback(this)
            .showResetOption(true)
            .setPendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .build()

        // Settings data from xml resource to keep a better overview
        SimpleSettings(this, config).show(R.xml.preferences)
        /*
        Apply custom activity transitions while opening. Custom activity transitions while closing
        can be specified by using the setPendingTransition() method on the config object
        */
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun openSettingsPaged() {
        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .setOptionsMenu(R.menu.menu_settings, this)
            .showResetOption(true)
            .setIconSpaceReservedByDefault(false)
            .build()

        // Programmatic settings data (especially useful for generating settings options at runtime)
        SimpleSettings(this, config).show {
            Header {
                layoutResource = R.layout.header
            }
            Section {
                title = "Section"
                Page {
                    title = "Page 1"
                    summary = "Demo summary 1"
                    displayHomeAsUpEnabled = false
                    Header {
                        layoutResource = R.layout.header
                    }
                    Section {
                        title = "Demo subsection"
                        MSListPref {
                            title = "MSListPreference"
                            simpleSummaryProvider = true
                            entries = listOf("Apple", "Banana", "Avocado", "Pineapple")
                        }
                        TextPref {
                            title = "LibsClickListener"
                            onClick = LibsClickListener(this@MainActivity)
                        }
                    }
                }
                Page {
                    title = "Page 2"
                    summary = "Demo summary 2"
                    activityTitle = "Page 2.2"
                    Section {
                        DropDownPref {
                            title = "DropDownPreference"
                            simpleSummaryProvider = true
                            entries = listOf("Apple", "Banana", "Avocado", "Pineapple")
                        }
                        SeekBarPref {
                            title = "SeekBarPreference"
                            summary = "Summary"
                            min = 1
                            max = 30
                            defaultValue = 22
                            showValue = true
                        }
                    }
                }
            }
            Section {
                InputPref {
                    title = "InputPreference"
                    summary = "Click to set text"
                    defaultValue = "Default text"
                }
                TextPref {
                    title = "PlayStoreClickListener"
                    summary = "Click here to open PlayStore site of this app"
                    onClick = PlayStoreClickListener(this@MainActivity)
                }
            }
        }
    }

    override fun onSettingsOptionsItemSelected(@IdRes itemId: Int) {
        when(itemId) {
            R.id.actionGitHub -> openGitHubPage()
            R.id.actionRate -> openGooglePlayAppSite()
        }
    }

    override fun onPreferenceClick(context: Context, key: String): Preference.OnPreferenceClickListener? {
        return when(key) {
            "preference" -> WebsiteClickListener(this, getString(R.string.github_link))
            "libs" -> LibsClickListener(this)
            "checkbox_preference" -> Preference.OnPreferenceClickListener {
                // Use the passed context for showing dialogs
                AlertDialog.Builder(context)
                    .setTitle("Test dialog")
                    .setMessage("Test dialog message")
                    .setPositiveButton("OK", null)
                    .show()
                true
            }
            "dialog_preference" -> DialogClickListener("Test", "This is a test", DialogClickListener.Type.OK)
            else -> super.onPreferenceClick(context, key)
        }
    }

    override fun onDialogButtonClicked(button: DialogClickListener.Button) {
        when(button) {
            DialogClickListener.Button.POSITIVE ->
                Toast.makeText(this, R.string.yes, Toast.LENGTH_SHORT).show()
            DialogClickListener.Button.NEGATIVE ->
                Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show()
            DialogClickListener.Button.NEUTRAL ->
                Toast.makeText(this, R.string.cancel, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGitHubPage() {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getString(R.string.github_link))
        })
    }
}