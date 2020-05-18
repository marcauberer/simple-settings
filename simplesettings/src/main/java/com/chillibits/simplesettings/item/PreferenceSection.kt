package com.chillibits.simplesettings.item

class PreferenceSection {

    // Attributes
    var title: String = "Section title"
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
}