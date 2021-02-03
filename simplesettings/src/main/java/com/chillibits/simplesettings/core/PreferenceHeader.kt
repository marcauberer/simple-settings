package com.chillibits.simplesettings.core

import android.content.Context
import androidx.annotation.LayoutRes

class PreferenceHeader(
        context: Context,
        iconSpaceReservedByDefault: Boolean
): PreferenceElement(context, iconSpaceReservedByDefault) {

    // Attributes
    @LayoutRes
    var layoutResource: Int? = null
    var height: Int = 240
}