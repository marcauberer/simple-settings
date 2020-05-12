package com.chillibits.simplesettings.core

import android.content.Context
import android.content.Intent
import com.chillibits.simplesettings.item.SimpleInputPreference
import com.chillibits.simplesettings.item.SimplePreference
import com.chillibits.simplesettings.item.SimpleSwitchPreference
import com.chillibits.simplesettings.item.SimpleTextPreference
import com.chillibits.simplesettings.ui.SimpleSettingsActivity

class SimpleSettings(
    val context: Context,
    val config: SimpleSettingsConfig = DEFAULT_CONFIG
) {

    // Variables as objects
    private val data = ArrayList<SimplePreference>()

    // Variables

    fun show() {
        context.startActivity(Intent(context, SimpleSettingsActivity::class.java).apply {

        })
    }

    fun show(func: SimpleSettings.() -> Unit): SimpleSettings = apply {
        this.func()
        this.show()
    }

    companion object {
        @JvmStatic val DEFAULT_CONFIG = SimpleSettingsConfig.Builder().build()
    }

    // ------------------------------------ Preferences types --------------------------------------

    fun SimpleTextPref(func: SimpleTextPreference.() -> Unit) = SimpleTextPreference().apply {
        this.func()
        data.add(this)
    }

    fun SimpleSwitchPref(func: SimpleSwitchPreference.() -> Unit) = SimpleSwitchPreference().apply {
        this.func()
        data.add(this)
    }

    fun SimpleInputPref(func: SimpleInputPreference.() -> Unit) = SimpleInputPreference().apply {
        this.func()
        data.add(this)
    }
}