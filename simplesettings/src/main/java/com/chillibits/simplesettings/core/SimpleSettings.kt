/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context
import android.content.Intent
import androidx.annotation.XmlRes
import androidx.preference.Preference
import com.chillibits.simplesettings.ui.SimpleSettingsActivity

class SimpleSettings(
    private val context: Context,
    configuration: SimpleSettingsConfig = DEFAULT_CONFIG
) {

    init {
        // Init with configuration
        config = configuration
        // Reset static attributes
        preferenceRes = 0
        mainSections.clear()
        pages.clear()
    }

    private fun show() {
        context.startActivity(Intent(context, SimpleSettingsActivity::class.java))
    }

    fun show(func: SimpleSettings.() -> Unit): SimpleSettings = apply {
        this.func()
        this.show()
    }

    fun show(@XmlRes preferenceResource: Int) {
        preferenceRes = preferenceResource
        context.startActivity(Intent(context, SimpleSettingsActivity::class.java))
    }

    companion object {
        @JvmStatic val DEFAULT_CONFIG = SimpleSettingsConfig.Builder().build()
        var config: SimpleSettingsConfig = DEFAULT_CONFIG
        @XmlRes
        var preferenceRes = 0
        var mainSections = ArrayList<PreferenceSection>()
        var pages = ArrayList<PreferencePage>()
    }

    // ----------------------------------- Preference section --------------------------------------

    fun Section(func: PreferenceSection.() -> Unit) = PreferenceSection(context, config.iconSpaceReservedByDefault).apply {
        this.func()
        mainSections.add(this)
    }

    fun Page(func: PreferencePage.() -> Unit) = PreferencePage(context).apply {
        this.func()
        pages.add(this)
    }
}