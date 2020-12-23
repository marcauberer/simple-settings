/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context
import com.chillibits.simplesettings.item.SimplePreference

class PreferencePage(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(iconSpaceReservedByDefault) {

    // Attributes
    var subSections = ArrayList<PreferenceSection>()
    var activityTitle = ""
    var displayHomeAsUpEnabled = true

    // ----------------------------------- Preference section --------------------------------------

    fun Section(func: PreferenceSection.() -> Unit)
            = PreferenceSection(context, SimpleSettings.config.iconSpaceReservedByDefault).apply {
        this.func()
        subSections.add(this)
    }
}