/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.preference.Preference
import java.io.Serializable

class SimpleSettingsConfig {

    // Attributes
    var activityTitle: String? = null
    @MenuRes
    var optionsMenuRes: Int? = null
    var optionsMenuCallback: OptionsItemSelectedCallback? = null
    var preferenceCallback: PreferenceCallback? = null
    var displayHomeAsUpEnabled = true
    var showResetOption = false
    var iconSpaceReservedByDefault = true
    var enableMSListPreferenceSummaryProvider = true
    @AnimRes var pendingTransitionEnterAnim: Int? = null
    @AnimRes var pendingTransitionExitAnim: Int? = null

    // Enums
    enum class PreferenceAction { CLICK }

    // Interfaces
    interface OptionsItemSelectedCallback: Serializable {
        fun onSettingsOptionsItemSelected(@IdRes itemId: Int)
    }
    interface PreferenceCallback: Serializable {
        // Callback called, if any event happens
        fun onPreferenceAction(context: Context, key: String, action: PreferenceAction) {}
        // Callbacks for specific events
        fun onPreferenceClick(context: Context, key: String): Preference.OnPreferenceClickListener? = null
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

        fun setPreferenceCallback(callback: PreferenceCallback): Builder {
            config.preferenceCallback = callback
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

        fun setPendingTransition(@AnimRes enterAnim: Int, @AnimRes exitAnim: Int): Builder {
            config.pendingTransitionEnterAnim = enterAnim
            config.pendingTransitionExitAnim = exitAnim
            return this
        }

        fun disableMSListPreferenceSummaryProvider(): Builder {
            config.enableMSListPreferenceSummaryProvider = false
            return this
        }

        fun build() = config
    }
}