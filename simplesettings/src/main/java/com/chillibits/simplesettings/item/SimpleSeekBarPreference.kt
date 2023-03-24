/*
 * Copyright © Marc Auberer 2020-2023. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context

class SimpleSeekBarPreference(
    context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(context, iconSpaceReservedByDefault) {

    // Attributes
    var min = 0

    var max = 100

    var defaultValue = 0

    var showValue = false
}