/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context
import androidx.annotation.ArrayRes

class SimpleDropDownPreference(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(context, iconSpaceReservedByDefault) {

    // Attributes
    var defaultIndex = 0

    var simpleSummaryProvider = false

    var entries = emptyList<String>()
    @ArrayRes var entriesRes = 0
        set(value) { entries = context.resources.getStringArray(value).toList() }
}