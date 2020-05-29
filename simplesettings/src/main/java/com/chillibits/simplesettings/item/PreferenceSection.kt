/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context

class PreferenceSection(
    private val context: Context,
    val iconSpaceReserved: Boolean
) {

    // Attributes
    var title = ""
    var enabled = true
    val items = ArrayList<SimplePreference>()

    // ----------------------------------------- Item types ----------------------------------------

    fun TextPref(func: SimpleTextPreference.() -> Unit)
            = SimpleTextPreference(iconSpaceReserved).apply {
        this.func()
        items.add(this)
    }

    fun SwitchPref(func: SimpleSwitchPreference.() -> Unit)
            = SimpleSwitchPreference(iconSpaceReserved).apply {
        this.func()
        items.add(this)
    }

    fun InputPref(func: SimpleInputPreference.() -> Unit)
            = SimpleInputPreference(iconSpaceReserved).apply {
        this.func()
        items.add(this)
    }

    fun ListPref(func: SimpleListPreference.() -> Unit)
            = SimpleListPreference(iconSpaceReserved).apply {
        this.func()
        items.add(this)
    }

    fun LibsPref(func: SimpleLibsPreference.() -> Unit)
            = SimpleLibsPreference(context, iconSpaceReserved).apply {
        this.func()
        items.add(this)
    }
}