/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context

class PreferenceSection(
    private val context: Context
) {

    // Attributes
    var title = ""
    var enabled = true
    val items = ArrayList<SimplePreference>()

    // ----------------------------------------- Item types ----------------------------------------

    fun SimpleTextPref(func: SimpleTextPreference.() -> Unit) = SimpleTextPreference().apply {
        this.func()
        items.add(this)
    }

    fun SimpleSwitchPref(func: SimpleSwitchPreference.() -> Unit) = SimpleSwitchPreference().apply {
        this.func()
        items.add(this)
    }

    fun SimpleInputPref(func: SimpleInputPreference.() -> Unit) = SimpleInputPreference().apply {
        this.func()
        items.add(this)
    }

    fun SimpleLibsPreference(func: SimpleLibsPreference.() -> Unit) = SimpleLibsPreference(context).apply {
        this.func()
        items.add(this)
    }
}