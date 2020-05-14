package com.chillibits.simplesettings.ui

import android.os.Bundle
import androidx.preference.*
import com.chillibits.simplesettings.item.*

class SimpleSettingsFragment : PreferenceFragmentCompat() {

    // Variables as objects

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        arguments?.let { args ->
            if(args.containsKey("xml")) {
                // Inflate preferences from xml resource
                val xmlResource = args.getInt("xml")
                setPreferencesFromResource(xmlResource, rootKey)
            } else {
                // Build preferences from sections array
                val screen = preferenceManager.createPreferenceScreen(context)

                // Add sections
                val sections = args.getSerializable("sections") as List<PreferenceSection>
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
    }

    // -------------------------------- Preference creator methods ---------------------------------

    private fun createDefaultPreference(sp: SimplePreference) = Preference(context)

    private fun createSimpleTextPreference(sp: SimpleTextPreference) = Preference(context).apply {
        title = sp.title
        summary = sp.summary
    }

    private fun createSimpleSwitchPreference(sp: SimpleSwitchPreference) = SwitchPreferenceCompat(context).apply {
        title = sp.title
        summary = sp.summary
        setDefaultValue(sp.defaultValue)
    }

    private fun createSimpleInputPreference(sp: SimpleInputPreference) = EditTextPreference(context).apply {
        title = sp.title
        summary = sp.summary
        setDefaultValue(sp.defaultValue)
    }
}