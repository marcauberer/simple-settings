/*
 * Copyright © Marc Auberer 2020-2021. All rights reserved
 */

package com.chillibits.simplesettingssample

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.Observer
import androidx.preference.Preference
import com.chillibits.simplesettings.clicklistener.DialogClickListener
import com.chillibits.simplesettings.clicklistener.LibsClickListener
import com.chillibits.simplesettings.clicklistener.PlayStoreClickListener
import com.chillibits.simplesettings.clicklistener.WebsiteClickListener
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.core.SimpleSettingsConfig
import com.chillibits.simplesettings.item.SimpleSwitchPreference
import com.chillibits.simplesettings.tool.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), SimpleSettingsConfig.OptionsItemSelectedCallback,
    SimpleSettingsConfig.PreferenceCallback, DialogClickListener.DialogResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize toolbar
        setSupportActionBar(toolbar)

        // Set window insets
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.decorView.setOnApplyWindowInsetsListener { v, insets ->
                v.setPadding(0, 0, insets.systemWindowInsetRight, insets.systemWindowInsetBottom)
                toolbar.setPadding(0, insets.systemWindowInsetTop, 0, 0)
                insets.consumeSystemWindowInsets()
            }
        }

        // Initialize number picker
        numberPicker.apply {
            minValue = 1
            maxValue = 10
            value = 3
        }

        subscribeToPreferenceValues()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_github -> openGitHubPage()
        }
        return super.onOptionsItemSelected(item)
    }

    @Preview
    @Composable
    private fun MainView() {
        val context = LocalContext.current
        ConstraintLayout(
            constraintSet = ConstraintSet {
                val layout = createRefFor("layout")
                constrain(layout) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            }
        ) {
            Column(
                modifier = Modifier.layoutId("layout")
            ) {
                Row(modifier = Modifier.align(CenterHorizontally)) {
                    Text(stringResource(R.string.number_of_switch_preferences))
                    NumberPicker(context)
                }
                Button(onClick = {openSettingsCodeConfig() }, modifier = Modifier.align(CenterHorizontally)) {
                    Text(stringResource(R.string.open_settings_code))
                }
                Button(onClick = {openSettingsXmlConfig() }, modifier = Modifier.align(CenterHorizontally)) {
                    Text(stringResource(R.string.open_settings_xml))
                }
                Button(onClick = {openSettingsPaged() }, modifier = Modifier.align(CenterHorizontally)) {
                    Text(stringResource(R.string.open_settings_paged))
                }
                Spacer(Modifier.height(50.dp))
                Text(stringResource(R.string.value_input_preference_), modifier = Modifier.align(CenterHorizontally))
                Text("test", modifier = Modifier.align(CenterHorizontally))
            }
        }
    }

    fun openSettingsCodeConfig() {
        // Get number from number picker
        val numberOfSwitchPreferences = numberPicker.value

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

    fun openSettingsXmlConfig() {
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

    fun openSettingsPaged() {
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

    private fun subscribeToPreferenceValues() {
        getPrefObserver( this, "inputpreference", Observer<String> {
            inputPreferenceValue.text = getString(R.string.value_input_preference_, it)
        })
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
            DialogClickListener.Button.POSITIVE -> {
                Toast.makeText(this, R.string.yes, Toast.LENGTH_SHORT).show()
            }
            DialogClickListener.Button.NEGATIVE -> {
                Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show()
            }
            DialogClickListener.Button.NEUTRAL -> {
                Toast.makeText(this, R.string.cancel, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGitHubPage() {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getString(R.string.github_link))
        })
    }
}
