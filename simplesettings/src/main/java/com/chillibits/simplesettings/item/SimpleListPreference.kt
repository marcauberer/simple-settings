/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class SimpleListPreference(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(context, iconSpaceReservedByDefault) {

    // Attributes
    var defaultIndex = 0

    var dialogTitle = ""
    @StringRes
    var dialogTitleRes = 0
        set(value) { dialogTitle = context.getString(value) }

    var dialogMessage: String? = null
    @StringRes
    var dialogMessageRes = 0
        set(value) { dialogMessage = context.getString(value) }

    var dialogIcon: Drawable? = null
    @DrawableRes
    var dialogIconRes = 0
        set(value) { dialogIcon = ContextCompat.getDrawable(context, value) }

    @LayoutRes
    var dialogLayoutRes = 0

    var simpleSummaryProvider = false

    var entries: List<String> = emptyList()
    @ArrayRes var entriesRes = 0
        set(value) { entries = context.resources.getStringArray(value).toList() }
}