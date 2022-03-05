/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context
import androidx.annotation.StringRes
import com.chillibits.simplesettings.R

class SimpleColorPreference(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(context, iconSpaceReservedByDefault) {

    // Attributes
    var attachAlphaSlideBar = false

    var attachBrightnessSlideBar = false

    var colorBoxRadius = 26.0

    var dialogNegative = context.getString(R.string.cancel)
    @StringRes
    var dialogNegativeRes = 0
        set(value) { dialogNegative = context.getString(value) }

    var dialogPositive = context.getString(R.string.ok)
    @StringRes
    var dialogPositiveRes = 0
        set(value) { dialogPositive = context.getString(value) }

    var dialogTitle = title
    @StringRes
    var dialogTitleRes = 0
        set(value) { dialogTitle = context.getString(value) }
}