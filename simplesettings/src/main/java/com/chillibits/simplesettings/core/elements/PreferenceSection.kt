/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.core.elements

import android.content.Context
import androidx.annotation.StringRes
import com.chillibits.simplesettings.item.*

/**
 * Preference Section. Represents a group of preference items.
 * More information: https://github.com/marcauberer/simple-settings/wiki/PreferenceSection
 */
class PreferenceSection(
    context: Context,
    iconSpaceReserved: Boolean
): PreferenceElement(context, iconSpaceReserved) {

    // Attributes
    var title = ""
    @StringRes var titleRes = 0
        set(value) { title = context.getString(value) }

    var enabled = true

    val items = ArrayList<SimplePreference>()

    // ----------------------------------------- Item types ----------------------------------------

    /**
     * Page element for nested settings screens.
     * More information: https://github.com/marcauberer/simple-settings/wiki/PreferencePage
     */
    fun Page(func: PreferencePage.() -> Unit)
            = PreferencePage(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Normal text preference with nothing much to see.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleTextPreference
     */
    fun TextPref(func: SimpleTextPreference.() -> Unit)
            = SimpleTextPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Switch Preference, which can be either on or off
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleSwitchPreference
     */
    fun SwitchPref(func: SimpleSwitchPreference.() -> Unit)
            = SimpleSwitchPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Checkbox Preference, which can be either on or off
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleCheckboxPreference
     */
    fun CheckboxPref(func: SimpleCheckboxPreference.() -> Unit)
            = SimpleCheckboxPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Input Preference for text inputs.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleInputPreference
     */
    fun InputPref(func: SimpleInputPreference.() -> Unit)
            = SimpleInputPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * List preference, which shows a list for the user to choose one item from.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleListPreference
     */
    fun ListPref(func: SimpleListPreference.() -> Unit)
            = SimpleListPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Multi Selection List preference. Same as List preference, but the user can select multiple items.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleMSListPreference
     */
    fun MSListPref(func: SimpleMSListPreference.() -> Unit)
            = SimpleMSListPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Drop Down Preference. Also for single-choice lists selections.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleDropDownPreference
     */
    fun DropDownPref(func: SimpleDropDownPreference.() -> Unit)
            = SimpleDropDownPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }

    /**
     * Seek Bar Preference. For selecting a number between two customizable bounds.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleSeekbarPreference
     */
    fun SeekBarPref(func: SimpleSeekBarPreference.() -> Unit) = SimpleSeekBarPreference(context, iconSpaceReserved).apply {
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

    /**
     * Color Preference, for picking a color via a color picker dialog.
     * More information: https://github.com/marcauberer/simple-settings/wiki/SimpleColorPreference
     */
    fun ColorPref(func: SimpleColorPreference.() -> Unit)
            = SimpleColorPreference(context, iconSpaceReserved).apply {
        func()
        items.add(this)
    }
}