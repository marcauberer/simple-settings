package com.chillibits.simplesettings.ui

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.preference.*
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.item.SimpleInputPreference
import com.chillibits.simplesettings.item.SimplePreference
import com.chillibits.simplesettings.item.SimpleSwitchPreference
import com.chillibits.simplesettings.item.SimpleTextPreference
import com.chillibits.simplesettings.tool.toCamelCase

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
                        is SimpleTextPreference -> genTextPref(item)
                        is SimpleSwitchPreference -> genSwitchPref(item)
                        is SimpleInputPreference -> genInputPref(item)
                        else -> Preference(context)
                    }
                    category.addPreference(preferenceItem)
                }
            }

            preferenceScreen = screen
        }
    }

    // -------------------------------- Preference creator methods ---------------------------------

    private fun genTextPref(sp: SimpleTextPreference) = Preference(context).apply {
        initializeGeneralAttributes(sp, this)
    }

    private fun genSwitchPref(sp: SimpleSwitchPreference) = SwitchPreferenceCompat(context).apply {
        initializeGeneralAttributes(sp, this)
        if(sp.summaryOff.isNotEmpty() && sp.summaryOn.isNotEmpty()) {
            summaryOff = sp.summaryOff
            summaryOn = sp.summaryOn
        }
        setDefaultValue(sp.defaultValue)
    }

    private fun genInputPref(sp: SimpleInputPreference) = EditTextPreference(context).apply {
        // Initialize attributes
        if(sp.dialogTitle.isBlank()) sp.dialogTitle = sp.title
        if(sp.dialogIcon == null && sp.dialogIconRes != 0)
            sp.dialogIcon = ResourcesCompat.getDrawable(resources, sp.dialogIconRes, context.theme)

        // Apply attributes
        initializeGeneralAttributes(sp, this)
        dialogTitle = sp.dialogTitle
        dialogMessage = sp.dialogMessage
        dialogIcon = sp.dialogIcon
        if(sp.dialogLayoutRes != 0) dialogLayoutResource = sp.dialogLayoutRes
        setDefaultValue(sp.defaultValue)
    }

    // -------------------------------------- Utility methods --------------------------------------

    private fun initializeGeneralAttributes(sp: SimplePreference, pref: Preference) {
        pref.apply {
            key = if(sp.key.isBlank()) sp.title.toCamelCase() else sp.key
            title = sp.title
            summary = sp.summary
            val dependentFrom = preferenceScreen.findPreference<SwitchPreferenceCompat>(sp.dependency)
            dependentFrom?.setOnPreferenceChangeListener { _, newValue ->
                isEnabled = newValue == true
                true
            }
            onPreferenceClickListener = sp.onClick
        }
    }
}