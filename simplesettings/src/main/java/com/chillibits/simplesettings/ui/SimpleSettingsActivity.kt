package com.chillibits.simplesettings.ui

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.simplesettings.R
import com.chillibits.simplesettings.core.SimpleSettings
import kotlinx.android.synthetic.main.toolbar.*

class SimpleSettingsActivity : AppCompatActivity() {

    // Variables as objects
    private val config = SimpleSettings.config

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_settings)

        // Initialize toolbar
        setSupportActionBar(toolbar)

        // Set window insets
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.decorView.setOnApplyWindowInsetsListener { v, insets ->
                v.setPadding(0, 0, 0, insets.systemWindowInsetBottom)
                toolbar.setPadding(0, insets.systemWindowInsetTop, 0, 0)
                insets.consumeSystemWindowInsets()
            }
        }

        // DisplayHomeAsUpEnabled
        if(config.displayHomeAsUpEnabled) supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize SettingsFragment
        initSettingsFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        config.optionsMenuRes?.let { menuInflater.inflate(it, menu) }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(val id = item.itemId) {
            android.R.id.home -> finish()
            else -> {
                // config.optionsMenuCallback?.onSettingsOptionsItemSelected(id)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsFragment, SimpleSettingsFragment())
            .commit()
    }
}
