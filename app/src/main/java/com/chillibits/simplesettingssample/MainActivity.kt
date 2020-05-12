package com.chillibits.simplesettingssample

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.core.SimpleSettingsConfig
import com.chillibits.simplesettings.item.SimpleSwitchPreference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), SimpleSettingsConfig.OptionsItemSelectedCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize toolbar
        setSupportActionBar(toolbar)

        // Setup layout components
        openSettings.setOnClickListener { openSettings() }
    }

    private fun openSettings() {
        val config = SimpleSettingsConfig.Builder()
            .displayHomeAsUpEnabled(true)
            .enableLibsActivityPreference()
            .setOptionsMenu(R.menu.menu_settings, this)
            .showResetOption(true)
            .build()

        val settings = SimpleSettings(this, config).show {
            SimpleSwitchPref {
                title = "Test"
                summary = "Dies ist ein Test1"
                defaultValue = SimpleSwitchPreference.ON
            }
            SimpleTextPref {
                title = "Test1"
                summary = "Dies ist ein Test1"
            }
        }
    }

    override fun onOptionsItemSelected(@IdRes itemId: Int) {
        when(itemId) {
            R.id.actionGitHub -> {

            }
        }
    }
}
