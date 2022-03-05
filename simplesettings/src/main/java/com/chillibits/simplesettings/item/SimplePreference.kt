/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.preference.Preference

abstract class SimplePreference(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
) {

    // Attributes
    var key = ""
    @StringRes
    var keyRes = 0
        set(value) { key = context.getString(value) }

    var title = ""
    @StringRes
    var titleRes = 0
        set(value) { title = context.getString(value) }

    var summary = ""
    @StringRes
    var summaryRes = 0
        set(value) { summary = context.getString(value) }

    var dependency = ""
    @StringRes
    var dependencyRes = 0
        set(value) { dependency = context.getString(value) }

    var enabled = true

    @DrawableRes
    var icon: Int = 0

    var iconSpaceReserved = iconSpaceReservedByDefault

    var onClick: Preference.OnPreferenceClickListener? = null

    companion object {
        const val SUMMARY_VALUE = "%s"
    }
}