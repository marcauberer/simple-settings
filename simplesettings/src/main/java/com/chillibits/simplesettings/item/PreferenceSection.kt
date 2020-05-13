package com.chillibits.simplesettings.item

import java.io.Serializable

class PreferenceSection: Serializable {

    // Attributes
    var title: String = "Section title"
    private val items = ArrayList<SimplePreference>()

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
}