package com.chillibits.simplesettings.item

class SimpleSwitchPreference: SimplePreference() {
    // Attributes
    var defaultValue = OFF

    companion object {
        const val OFF = false
        const val ON = true
    }
}