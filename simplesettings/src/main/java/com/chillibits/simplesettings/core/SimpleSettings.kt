package com.chillibits.simplesettings.core

import android.content.Context
import android.content.Intent
import androidx.annotation.XmlRes
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

    private fun show() {
        context.startActivity(Intent(context, SimpleSettingsActivity::class.java).apply {
            putExtra("sections", sections)
            putExtra("config", config)
        })
    }

    fun show(func: SimpleSettings.() -> Unit): SimpleSettings = apply {
        this.func()
        this.show()
    }

    fun show(@XmlRes preferenceResource: Int) {
        context.startActivity(Intent(context, SimpleSettingsActivity::class.java).apply {
            putExtra("xml", preferenceResource)
            putExtra("config", config)
        })
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