package com.chillibits.simplesettings.ui

import android.os.Bundle
import androidx.preference.*
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.item.SimpleInputPreference
import com.chillibits.simplesettings.item.SimplePreference
import com.chillibits.simplesettings.item.SimpleSwitchPreference
import com.chillibits.simplesettings.item.SimpleTextPreference

class SimpleSettingsFragment : PreferenceFragmentCompat() {

    // Variables as objects
    private val preferenceRes = SimpleSettings.preferenceRes
    private val sections = SimpleSettings.sections

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if(preferenceRes != 0) {
            // Inflate preferences from xml resource
            setPreferencesFromResource(preferenceRes, rootKey)
        } else {
            // Build preferences from sections array
            val screen = preferenceManager.createPreferenceScreen(context)

            // Add sections
            sections.forEach { section ->
                // Add category itself
                val category = PreferenceCategory(context)
                category.title = section.title
                screen.addPreference(category)

                // Add items to category
                section.items.forEach { item ->
                    val preferenceItem = when(item) {
                        is SimpleTextPreference -> createSimpleTextPreference(item)
                        is SimpleSwitchPreference -> createSimpleSwitchPreference(item)
                        is SimpleInputPreference -> createSimpleInputPreference(item)
                        else -> createDefaultPreference(item)
                    }
                    category.addPreference(preferenceItem)
                }
            }

            preferenceScreen = screen
        }
    }

    // -------------------------------- Preference creator methods ---------------------------------

    private fun createDefaultPreference(sp: SimplePreference) = Preference(context)

    private fun createSimpleTextPreference(sp: SimpleTextPreference) = Preference(context).apply {
        title = sp.title
        summary = sp.summary
        onPreferenceClickListener = Preference.OnPreferenceClickListener {

            true
        }
    }

    private fun createSimpleSwitchPreference(sp: SimpleSwitchPreference) = SwitchPreferenceCompat(context).apply {
        if(sp.summaryOff.isNotEmpty() && sp.summaryOn.isNotEmpty()) {
            summaryOff = sp.summaryOff
            summaryOn = sp.summaryOn
        } else {
            summary = sp.summary
        }
        title = sp.title
        setDefaultValue(sp.defaultValue)
    }

    private fun createSimpleInputPreference(sp: SimpleInputPreference) = EditTextPreference(context).apply {
        title = sp.title
        summary = sp.summary
        setDefaultValue(sp.defaultValue)
    }
}