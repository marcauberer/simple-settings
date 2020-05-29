/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import java.io.Serializable

class SimpleSettingsConfig {

    // Attributes
    var activityTitle: String? = null
    @MenuRes
    var optionsMenuRes: Int? = null
    var optionsMenuCallback: OptionsItemSelectedCallback? = null
    var displayHomeAsUpEnabled = true
    var showResetOption = false
    var iconSpaceReservedByDefault = true

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
            config.optionsMenuCallback = callback
            return this
        }

        fun setActivityTitle(title: String): Builder {
            config.activityTitle = title
            return this
        }

        fun setActivityTitle(context: Context, @StringRes stringRes: Int) =
            setActivityTitle(context.getString(stringRes))

        fun displayHomeAsUpEnabled(enabled: Boolean): Builder {
            config.displayHomeAsUpEnabled = enabled
            return this
        }

        fun showResetOption(enabled: Boolean): Builder {
            config.showResetOption = enabled
            return this
        }

        fun setIconSpaceReservedByDefault(iconSpaceReservedByDefault: Boolean): Builder {
            config.iconSpaceReservedByDefault = iconSpaceReservedByDefault
            return this
        }

        fun build() = config
    }
}