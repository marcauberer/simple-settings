/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettingssample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.simplesettings.clicklistener.LibsClickListener
import com.chillibits.simplesettings.clicklistener.PlayStoreClickListener
import com.chillibits.simplesettings.clicklistener.WebsiteClickListener
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.core.SimpleSettingsConfig
import com.chillibits.simplesettings.item.SimpleSwitchPreference
import com.chillibits.simplesettings.tool.openGooglePlayAppSite
import com.chillibits.simplesettings.tool.toCamelCase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), SimpleSettingsConfig.OptionsItemSelectedCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize toolbar
        setSupportActionBar(toolbar)

        // Setup layout components
        openSettings1.setOnClickListener { openSettingsCodeConfig() }
        openSettings2.setOnClickListener { openSettingsXmlConfig() }
    }

    private fun openSettingsCodeConfig() {
        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .setOptionsMenu(R.menu.menu_settings, this)
            .showResetOption(true)
            .setIconSpaceReservedByDefault(false)
            .build()

        // Programmatic settings data (especially useful for generating settings options at runtime)
        SimpleSettings(this, config).show {
            Section {
                title = "Test section"
                for (i in 0..3) {
                    SwitchPref {
                        title = "Test 1.$i"
                        summaryOn = "This is a Test 1.$i - On"
                        summaryOff = "This is a Test 1.$i - Off"
                        defaultValue = if(i % 2 == 0) SimpleSwitchPreference.ON else SimpleSwitchPreference.OFF
                    }
                }
                TextPref {
                    title = "Test 2"
                    summary = "This is a Test 2"
                    onClick = WebsiteClickListener(this@MainActivity, getString(R.string.github_link))
                    dependency = "Test 1.3".toCamelCase()
                }
            }
            Section {
                InputPref {
                    title = "Test 3"
                    summary = "This is a Test 3"
                }
                TextPref {
                    title = "PlayStore test"
                    summary = "Click here to open PlayStore site of this app"
                    onClick = PlayStoreClickListener(this@MainActivity)
                }
                TextPref {
                    title = "Libs test"
                    summary = "Click here to open PlayStore site of this app"
                    onClick = LibsClickListener(this@MainActivity)
                }
                LibsPref {
                    activityTitle = "Test"
                    edgeToEdge = true
                }
            }
        }
    }

    private fun openSettingsXmlConfig() {
        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .setOptionsMenu(R.menu.menu_settings, this)
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

    private fun openGitHubPage() {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(getString(R.string.github_link))
        })
    }
}
