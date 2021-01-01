/*
 * Copyright © Marc Auberer 2020-2021. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context
import com.chillibits.simplesettings.item.SimplePreference

/**
 * Page element for nested settings screens.
 * More information: https://github.com/marcauberer/simple-settings/wiki/PreferencePage
 */
class PreferencePage(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(iconSpaceReservedByDefault) {

    // Attributes
    var subSections = ArrayList<PreferenceSection>()
    var activityTitle = ""
    var displayHomeAsUpEnabled = true

    // ----------------------------------- Preference section --------------------------------------

    /**
     * Preference Section. Represents a group of preference items.
     * More information: https://github.com/marcauberer/simple-settings/wiki/PreferenceSection
     */
    fun Section(func: PreferenceSection.() -> Unit)
            = PreferenceSection(context, SimpleSettings.config.iconSpaceReservedByDefault).apply {
        this.func()
        subSections.add(this)
    }
}