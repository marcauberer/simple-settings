/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context
import android.content.Intent
import androidx.annotation.XmlRes
import com.chillibits.simplesettings.core.elements.PreferenceElement
import com.chillibits.simplesettings.core.elements.PreferenceHeader
import com.chillibits.simplesettings.core.elements.PreferenceSection
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
        sections.clear()
    }

    /**
     * Build and show the preference screen
     */
    private fun show() {
        context.startActivity(Intent(context, SimpleSettingsActivity::class.java))
    }

    /**
     * Build and show the preference screen
     */
    fun show(func: SimpleSettings.() -> Unit): SimpleSettings = apply {
        func()
        show()
    }

    /**
     * Build and show the preference screen
     */
    fun show(@XmlRes preferenceResource: Int) {
        preferenceRes = preferenceResource
        show()
    }

    companion object {
        @JvmStatic val DEFAULT_CONFIG = SimpleSettingsConfig.Builder().build()
        var config: SimpleSettingsConfig = DEFAULT_CONFIG
        @XmlRes
        var preferenceRes = 0
        var sections = ArrayList<PreferenceElement>()
    }

    // ----------------------------------- Preference section --------------------------------------

    /**
     * Preference Section. Represents a group of preference items.
     * More information: https://github.com/marcauberer/simple-settings/wiki/PreferenceSection
     */
    fun Section(func: PreferenceSection.() -> Unit)
            = PreferenceSection(context, config.iconSpaceReservedByDefault).apply {
        func()
        sections.add(this)
    }

    /**
     * Preference Header. Represents the header of the main settings screen.
     * More information: https://github.com/marcauberer/simple-settings/wiki/PreferenceHeader
     */
    fun Header(func: PreferenceHeader.() -> Unit) = PreferenceHeader(context, config.iconSpaceReservedByDefault).apply {
        func()
        sections.add(this)
    }
}