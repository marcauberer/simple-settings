package com.chillibits.simplesettings.item

import java.io.Serializable

class SimpleSwitchPreference: SimplePreference(), Serializable {
    // Attributes
    var defaultValue = OFF

    companion object {
        const val OFF = false
        const val ON = true
    }
}