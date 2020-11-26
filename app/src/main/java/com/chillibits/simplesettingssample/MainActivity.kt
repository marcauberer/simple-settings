/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettingssample

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        // Initialize number picker
        numberPicker.apply {
            minValue = 1
            maxValue = 10
            value = 3
        }

        subscribeToPreferenceValues()
    }

    fun openSettingsCodeConfig(view: View) {
        // Get number from number picker
        val numberOfSwitchPreferences = numberPicker.value

        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .setOptionsMenu(R.menu.menu_settings, this)
            .showResetOption(true)
            .setIconSpaceReservedByDefault(false)
            .build()

        // Programmatic settings data (especially useful for generating settings options at runtime)
        SimpleSettings(this, config).show {
            Section {
                title = "Section"
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
    }

    fun openSettingsXmlConfig(view: View) {
        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .setOptionsMenu(R.menu.menu_settings, this)
            .setPreferenceCallback(this)
            .showResetOption(true)
            .build()

        // Settings data from xml resource to keep a better overview
        SimpleSettings(this, config).show(R.xml.preferences)
    }

    override fun onSettingsOptionsItemSelected(@IdRes itemId: Int) {
        when(itemId) {
            R.id.actionGitHub -> openGitHubPage()
            R.id.actionRate -> openGooglePlayAppSite()
        }
    }

    private fun subscribeToPreferenceValues() {
        getPrefObserver( "inputpreference", Observer<String> {
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
