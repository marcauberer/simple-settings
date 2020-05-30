/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.item

class SimpleDropDownPreference(
    iconSpaceReservedByDefault: Boolean
): SimplePreference(iconSpaceReservedByDefault) {

    // Attributes
    var defaultIndex = 0
    var simpleSummaryProvider = false
    var entries: List<String> = emptyList()
}