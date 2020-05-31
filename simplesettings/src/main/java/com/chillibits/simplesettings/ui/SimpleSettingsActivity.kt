/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.ui

import android.content.SharedPreferences
import android.content.res.XmlResourceParser
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.simplesettings.R
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.tool.Constants
import com.chillibits.simplesettings.tool.getPrefs
import com.chillibits.simplesettings.tool.toCamelCase
import kotlinx.android.synthetic.main.toolbar.*

class SimpleSettingsActivity : AppCompatActivity() {

    // Variables as objects
    private val config = SimpleSettings.config

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_settings)

        // Initialize toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = config.activityTitle ?: getString(R.string.settings)

        // Set window insets
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.decorView.setOnApplyWindowInsetsListener { v, insets ->
                v.setPadding(0, 0, insets.systemWindowInsetRight, insets.systemWindowInsetBottom)
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
        if(config.showResetOption)
            menu?.add(Menu.NONE, Constants.MENU_ITEM_RESET, 100, getString(R.string.resetSettings))
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(val id = item.itemId) {
            android.R.id.home -> finish()
            Constants.MENU_ITEM_RESET -> resetSettings()
            else -> config.optionsMenuCallback?.onSettingsOptionsItemSelected(id)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsFragment, SimpleSettingsFragment())
            .commit()
    }

    private fun resetSettings() {
        // Delete all affected keys from SharedPreferences
        getPrefs().edit().run {
            // Code-Config
            resetSettingsCodeConfig(this)
            // XML-Config
            resetSettingsXmlConfig(this)
            // Commit changes to disk
            commit()
        }

        // Re-inflate settings fragment
        initSettingsFragment()
    }

    private fun resetSettingsXmlConfig(e: SharedPreferences.Editor) {
        if (SimpleSettings.preferenceRes != 0) {
            val xrp = resources.getXml(SimpleSettings.preferenceRes)
            var eventType = -1
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    try {
                        if (xrp.name != "PreferenceScreen" && xrp.name != "PreferenceCategory") {
                            var key = ""
                            for (i in 0..xrp.attributeCount) {
                                if (xrp.getAttributeName(i) == "key") {
                                    key = xrp.getAttributeValue(i)
                                    break
                                }
                            }
                            e.remove(key)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                eventType = xrp.next()
            }
        }
    }

    private fun resetSettingsCodeConfig(e: SharedPreferences.Editor) {
        SimpleSettings.sections.forEach {
            it.items.forEach { item ->
                val key = if (item.key.isBlank()) item.title.toCamelCase() else item.key
                e.remove(key)
            }
        }
    }
}
