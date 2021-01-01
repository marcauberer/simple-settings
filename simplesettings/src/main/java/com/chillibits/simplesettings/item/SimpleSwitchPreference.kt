/*
 * Copyright © Marc Auberer 2020-2021. All rights reserved
 */

package com.chillibits.simplesettings.item

class SimpleSwitchPreference(
    iconSpaceReservedByDefault: Boolean
): SimplePreference(iconSpaceReservedByDefault) {

    // Attributes
    var defaultValue = OFF
    var summaryOn = ""
    var summaryOff = ""

    companion object {
        const val OFF = false
        const val ON = true
    }
}