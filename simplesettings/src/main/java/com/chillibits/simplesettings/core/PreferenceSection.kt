/*
 * Copyright © Marc Auberer 2020-2021. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context
import com.chillibits.simplesettings.item.*

/**
 * Preference Section. Represents a group of preference items.
 * More information: https://github.com/marcauberer/simple-settings/wiki/PreferenceSection
 */
class PreferenceSection(
    private val context: Context,
    val iconSpaceReserved: Boolean
) {

    // Attributes
    var title = ""
    var enabled = true
    val items = ArrayList<SimplePreference>()

    // ----------------------------------------- Item types ----------------------------------------

    /**
     * Page element for nested settings screens.
     * More information: https://github.com/marcauberer/simple-settings/wiki/PreferencePage
     */
    fun Page(func: PreferencePage.() -> Unit)
            = PreferencePage(context, iconSpaceReserved).apply {
        this.func()
        items.add(this)
    }

    /**
     * Normal text preference with nothing much to see.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleTextPreference
     */
    fun TextPref(func: SimpleTextPreference.() -> Unit)
            = SimpleTextPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Switch Preference, which can be either on or off
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleSwitchPreference
     */
    fun SwitchPref(func: SimpleSwitchPreference.() -> Unit)
            = SimpleSwitchPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Checkbox Preference, which can be either on or off
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleCheckboxPreference
     */
    fun CheckboxPref(func: SimpleCheckboxPreference.() -> Unit)
            = SimpleCheckboxPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Input Preference for text inputs.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleInputPreference
     */
    fun InputPref(func: SimpleInputPreference.() -> Unit)
            = SimpleInputPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * List preference, which shows a list for the user to choose one item from.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleListPreference
     */
    fun ListPref(func: SimpleListPreference.() -> Unit)
            = SimpleListPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Multi Selection List preference. Same as List preference, but the user can select multiple items.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleMSListPreference
     */
    fun MSListPref(func: SimpleMSListPreference.() -> Unit)
            = SimpleMSListPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Drop Down Preference. Also for single-choice lists selections.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleDropDownPreference
     */
    fun DropDownPref(func: SimpleDropDownPreference.() -> Unit)
            = SimpleDropDownPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Seek Bar Preference. For selecting a number between two customizable bounds.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleSeekbarPreference
     */
    fun SeekBarPref(func: SimpleSeekBarPreference.() -> Unit) = SimpleSeekBarPreference(iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Libs preference, which presents you an overview over the libraries, used by your app.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleLibsPreference
     */
    fun LibsPref(func: SimpleLibsPreference.() -> Unit)
            = SimpleLibsPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }
}