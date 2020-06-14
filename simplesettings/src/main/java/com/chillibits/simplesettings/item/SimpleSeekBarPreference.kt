/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.item

class SimpleSeekBarPreference(
    iconSpaceReservedByDefault: Boolean
): SimplePreference(iconSpaceReservedByDefault) {

    // Attributes
    var min = 0
    var max = 100
    var defaultValue = 0
    var showValue = false

}