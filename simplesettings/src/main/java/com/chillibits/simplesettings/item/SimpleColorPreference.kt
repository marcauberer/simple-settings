package com.chillibits.simplesettings.item

import android.content.Context
import com.chillibits.simplesettings.R

class SimpleColorPreference(
    context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(iconSpaceReservedByDefault) {

    // Attributes
    var attachAlphaSlideBar = false
    var attachBrightnessSlideBar = false
    var colorBoxRadius = 26.0
    var dialogNegative = context.getString(R.string.cancel)
    var dialogPositive = context.getString(R.string.ok)
    var dialogTitle = title
}