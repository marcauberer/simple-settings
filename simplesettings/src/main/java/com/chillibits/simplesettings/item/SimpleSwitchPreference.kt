package com.chillibits.simplesettings.item

class SimpleSwitchPreference: SimplePreference() {
    // Attributes
    var defaultValue = OFF
    var summaryOn = ""
    var summaryOff = ""

    companion object {
        const val OFF = false
        const val ON = true
    }
}