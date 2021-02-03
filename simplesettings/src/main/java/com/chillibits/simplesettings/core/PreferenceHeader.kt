package com.chillibits.simplesettings.core

import android.content.Context
import androidx.annotation.LayoutRes

/**
 * Page element for page headers / setting screen headers.
 * More information: https://github.com/marcauberer/simple-settings/wiki/PreferenceHeader
 */
class PreferenceHeader(
        context: Context,
        iconSpaceReservedByDefault: Boolean
): PreferenceElement(context, iconSpaceReservedByDefault) {

    // Attributes
    @LayoutRes
    var layoutResource: Int? = null
}