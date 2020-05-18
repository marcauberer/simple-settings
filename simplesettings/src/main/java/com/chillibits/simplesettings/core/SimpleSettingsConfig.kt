package com.chillibits.simplesettings.core

import android.os.Parcel
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import java.io.Serializable

class SimpleSettingsConfig() {

    // Attributes
    @MenuRes
    var optionsMenuRes: Int? = null
    // var optionsMenuCallback: OptionsItemSelectedCallback? = null
    var displayHomeAsUpEnabled = true
    var showResetOption = false

    constructor(parcel: Parcel) : this() {
        optionsMenuRes = parcel.readValue(Int::class.java.classLoader) as? Int
        displayHomeAsUpEnabled = parcel.readByte() != 0.toByte()
        showResetOption = parcel.readByte() != 0.toByte()
    }

    // Interfaces
    interface OptionsItemSelectedCallback: Serializable {
        fun onSettingsOptionsItemSelected(@IdRes itemId: Int)
    }

    class Builder {

        // Variables as objects
        private val config = SimpleSettingsConfig()

        // Variables

        fun setOptionsMenu(@MenuRes menuRes: Int, callback: OptionsItemSelectedCallback): Builder {
            config.optionsMenuRes = menuRes
            // config.optionsMenuCallback = callback
            return this
        }

        fun enableLibsActivityPreference(): Builder {

            return this
        }

        fun displayHomeAsUpEnabled(enabled: Boolean): Builder {
            config.displayHomeAsUpEnabled = enabled
            return this
        }

        fun showResetOption(enabled: Boolean): Builder {
            config.showResetOption = enabled
            return this
        }

        fun build() = config
    }
}