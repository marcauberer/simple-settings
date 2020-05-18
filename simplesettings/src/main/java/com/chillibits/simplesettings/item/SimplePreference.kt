package com.chillibits.simplesettings.item

abstract class SimplePreference {
    // Attributes
    var title = ""
    var summary = ""
    var onClick: OnPreferenceClickListener? = null

    interface OnPreferenceClickListener {
        fun onClick(pref: SimplePreference)
    }
}