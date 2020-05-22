/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.ui

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.preference.*
import com.chillibits.simplesettings.clicklistener.LibsClickListener
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.item.*
import com.chillibits.simplesettings.tool.toCamelCase
import com.mikepenz.aboutlibraries.LibsBuilder

class SimpleSettingsFragment : PreferenceFragmentCompat() {

    // Variables as objects
    private val preferenceRes = SimpleSettings.preferenceRes
    private val sections = SimpleSettings.sections

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if(preferenceRes != 0) {
            // Inflate preferences from xml resource
            setPreferencesFromResource(preferenceRes, rootKey)

            // Search for possible LibsPreference and attach LibsClickListener
            val libsPref = findPreference<Preference>("libs")
            libsPref?.onPreferenceClickListener = LibsClickListener(requireContext())
        } else {
            // Build preferences from sections array
            preferenceScreen = preferenceManager.createPreferenceScreen(context)

            // Add sections
            sections.forEach { section ->
                // Add category itself
                val category = PreferenceCategory(context)
                category.title = section.title
                category.isEnabled = section.enabled
                preferenceScreen.addPreference(category)

                // Add items to category
                section.items.forEach { item ->
                    val preferenceItem = when(item) {
                        is SimpleTextPreference -> genTextPref(item)
                        is SimpleSwitchPreference -> genSwitchPref(item)
                        is SimpleInputPreference -> genInputPref(item)
                        is SimpleLibsPreference -> genLibsPref(item)
                        else -> Preference(context)
                    }
                    category.addPreference(preferenceItem)
                }
            }

            // Post processing
            sections.forEach {
                it.items.forEach { item ->
                    if(item.dependency.isNotBlank()) {
                        val key = if(item.key.isBlank()) item.title.toCamelCase() else item.key
                        val pref = findPreference<Preference>(key)
                        pref?.dependency = item.dependency
                    }
                }
            }
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

    private fun genLibsPref(sp: SimpleLibsPreference) = Preference(context).apply {
        initializeGeneralAttributes(sp, this)
        setOnPreferenceClickListener {
            LibsBuilder().apply {
                activityTitle = sp.activityTitle
                edgeToEdge = sp.edgeToEdge
                aboutAppName = sp.aboutAppName
                aboutAppSpecial1 = sp.aboutAppSpecial1
                aboutAppSpecial1Description = sp.aboutAppSpecial1Description
                aboutAppSpecial2 = sp.aboutAppSpecial2
                aboutAppSpecial2Description = sp.aboutAppSpecial2Description
                aboutAppSpecial3 = sp.aboutAppSpecial3
                aboutAppSpecial3Description = sp.aboutAppSpecial3Description
                aboutDescription = sp.aboutDescription
                aboutMinimalDesign = sp.aboutMinimalDesign
                aboutShowIcon = sp.aboutShowIcon
                aboutShowVersion = sp.aboutShowVersion
                aboutShowVersionCode = sp.aboutShowVersionCode
                aboutShowVersionName = sp.aboutShowVersionName
                aboutVersionString = sp.aboutVersionString
                autoDetect = sp.autoDetect
                checkCachedDetection = sp.checkCachedDetection
                fields = sp.fields
                internalLibraries = sp.internalLibraries
                excludeLibraries = sp.excludeLibraries
                libraryComparator = sp.libraryComparator
                ownLibsActivityClass = sp.ownLibsActivityClass
                showLicense = sp.showLicense
                showLicenseDialog = sp.showLicenseDialog
                showLoadingProgress = sp.showLoadingProgress
                showVersion = sp.showVersion
                sort = sp.sort
            }.start(context)
            true
        }
    }

    private fun genListPref(sp: SimpleListPreference) = ListPreference(context).apply {

    }

    // -------------------------------------- Utility methods --------------------------------------

    private fun initializeGeneralAttributes(sp: SimplePreference, pref: Preference) {
        pref.apply {
            key = if(sp.key.isBlank()) sp.title.toCamelCase() else sp.key
            title = sp.title
            summary = sp.summary
            isEnabled = sp.enabled
            onPreferenceClickListener = sp.onClick
        }
    }
}