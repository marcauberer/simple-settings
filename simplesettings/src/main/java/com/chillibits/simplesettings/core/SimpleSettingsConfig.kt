package com.chillibits.simplesettings.core

import androidx.annotation.IdRes
import androidx.annotation.MenuRes

class SimpleSettingsConfig {

    // Attributes
    @MenuRes
    var optionsMenuRes: Int? = null
    var optionsMenuCallback: OptionsItemSelectedCallback? = null
    var displayHomeAsUpEnabled = true
    var showResetOption = false

    // Interfaces
    interface OptionsItemSelectedCallback {
        fun onOptionsItemSelected(@IdRes int: Int)
    }

    class Builder {

        // Variables as objects
        private val config = SimpleSettingsConfig()

        // Variables

        fun setOptionsMenu(@MenuRes menuRes: Int, callback: OptionsItemSelectedCallback): Builder {
            config.optionsMenuRes = menuRes
            config.optionsMenuCallback = callback
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