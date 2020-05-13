package com.chillibits.simplesettings.core

import android.content.Context
import android.content.Intent
import com.chillibits.simplesettings.item.PreferenceSection
import com.chillibits.simplesettings.ui.SimpleSettingsActivity
import java.io.Serializable

class SimpleSettings(
    val context: Context,
    val config: SimpleSettingsConfig = DEFAULT_CONFIG
): Serializable {

    // Variables as objects
    private val sections = ArrayList<PreferenceSection>()

    // Variables

    fun show() {
        context.startActivity(Intent(context, SimpleSettingsActivity::class.java).apply {
            putExtra("sections", sections)
            putExtra("config", config)
        })
    }

    fun show(func: SimpleSettings.() -> Unit): SimpleSettings = apply {
        this.func()
        this.show()
    }

    companion object {
        @JvmStatic val DEFAULT_CONFIG = SimpleSettingsConfig.Builder().build()
    }

    // ----------------------------------- Preference section --------------------------------------

    fun Section(func: PreferenceSection.() -> Unit) = PreferenceSection().apply {
        this.func()
        sections.add(this)
    }
}