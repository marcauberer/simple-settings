package com.chillibits.simplesettings.item

import androidx.preference.Preference

abstract class SimplePreference {

    // Attributes
    var key = ""
    var title = ""
    var summary = ""
    var dependency = ""
    var enabled = true
    var onClick: Preference.OnPreferenceClickListener? = null

    companion object {
        const val SUMMARY_VALUE = "%s"
    }
}