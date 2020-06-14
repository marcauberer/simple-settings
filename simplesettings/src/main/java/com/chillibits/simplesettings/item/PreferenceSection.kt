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
        func()
        items.add(this)
    }

    fun SwitchPref(func: SimpleSwitchPreference.() -> Unit)
            = SimpleSwitchPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    fun CheckboxPref(func: SimpleCheckboxPreference.() -> Unit)
            = SimpleCheckboxPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    fun InputPref(func: SimpleInputPreference.() -> Unit)
            = SimpleInputPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    fun ListPref(func: SimpleListPreference.() -> Unit)
            = SimpleListPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    fun MSListPref(func: SimpleMSListPreference.() -> Unit)
            = SimpleMSListPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    fun DropDownPref(func: SimpleDropDownPreference.() -> Unit)
            = SimpleDropDownPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    fun SeekBarPref(func: SimpleSeekBarPreference.() -> Unit) = SimpleSeekBarPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    fun LibsPref(func: SimpleLibsPreference.() -> Unit)
            = SimpleLibsPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }
}