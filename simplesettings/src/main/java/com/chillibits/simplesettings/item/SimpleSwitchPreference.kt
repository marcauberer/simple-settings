/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context
import androidx.annotation.StringRes

class SimpleSwitchPreference(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(context, iconSpaceReservedByDefault) {

    // Attributes
    var defaultValue = OFF

    var summaryOn = ""
    @StringRes
    var summaryOnRes = 0
        set(value) { summaryOn = context.getString(value) }

    var summaryOff = ""
    @StringRes
    var summaryOffRes = 0
        set(value) { summaryOff = context.getString(value) }

    companion object {
        const val OFF = false
        const val ON = true
    }
}