/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

class SimpleInputPreference(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(context, iconSpaceReservedByDefault) {

    // Attributes
    var defaultValue = ""
    @StringRes var defaultValueRes = 0
        set(value) { defaultValue = context.getString(value) }

    var dialogTitle = ""
    @StringRes var dialogTitleRes = 0
        set(value) { dialogTitle = context.getString(value) }

    var dialogMessage: String? = null
    @StringRes var dialogMessageRes = 0
        set(value) { dialogMessage = context.getString(value) }

    var dialogIcon: Drawable? = null
    @DrawableRes
    var dialogIconRes = 0
        set(value) { dialogIcon = context.getDrawable(value) }

    @LayoutRes  var dialogLayoutRes = 0
    var simpleSummaryProvider = false
}