/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.item

class SimpleCheckboxPreference(
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