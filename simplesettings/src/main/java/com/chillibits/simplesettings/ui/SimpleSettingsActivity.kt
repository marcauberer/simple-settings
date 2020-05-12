package com.chillibits.simplesettings.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.simplesettings.R
import kotlinx.android.synthetic.main.toolbar.*

class SimpleSettingsActivity : AppCompatActivity() {

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



        // Initialize SettingsFragment
        initSettingsFragment()
    }

    private fun initSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsFragment, SimpleSettingsFragment())
            .commit()
    }
}
