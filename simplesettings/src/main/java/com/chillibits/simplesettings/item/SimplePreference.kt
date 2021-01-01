/*
 * Copyright © Marc Auberer 2020-2021. All rights reserved
 */

package com.chillibits.simplesettings.item

import androidx.annotation.DrawableRes
import androidx.preference.Preference

abstract class SimplePreference(
    iconSpaceReservedByDefault: Boolean
) {

    // Attributes
    var key = ""
    var title = ""
    var summary = ""
    var dependency = ""
    var enabled = true
    @DrawableRes
    var icon: Int = 0
    var iconSpaceReserved = iconSpaceReservedByDefault
    var onClick: Preference.OnPreferenceClickListener? = null

    companion object {
        const val SUMMARY_VALUE = "%s"
    }
}