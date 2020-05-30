/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.ui

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.preference.*
import com.chillibits.simplesettings.clicklistener.LibsClickListener
import com.chillibits.simplesettings.core.SimpleMSListPreferenceSummaryProvider
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.item.*
import com.chillibits.simplesettings.tool.toCamelCase
import com.mikepenz.aboutlibraries.LibsBuilder

class SimpleSettingsFragment : PreferenceFragmentCompat() {

    // Variables as objects
    private val preferenceRes = SimpleSettings.preferenceRes
    private val sections = SimpleSettings.sections
    private val config = SimpleSettings.config

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if(preferenceRes != 0) {
            // Inflate preferences from xml resource
            setPreferencesFromResource(preferenceRes, rootKey)

            // Search for possible MSListPreferences and attach SimpleMSListPReferenceSummaryProvider
            configureMSListPreferences()

            // Search for possible LibsPreference and attach LibsClickListener
            configureLibsPreference()
        } else {
            // Build preferences from sections array
            preferenceScreen = preferenceManager.createPreferenceScreen(context)

            // Add sections
            sections.forEach { section ->
                // Add category itself
                val category = PreferenceCategory(context).apply {
                    title = section.title
                    isEnabled = section.enabled
                    isIconSpaceReserved = section.iconSpaceReserved
                }
                preferenceScreen.addPreference(category)

                // Add items to category
                section.items.forEach { item ->
                    val preferenceItem = when(item) {
                        is SimpleTextPreference -> genTextPref(item)
                        is SimpleSwitchPreference -> genSwitchPref(item)
                        is SimpleCheckboxPreference -> genCheckboxPref(item)
                        is SimpleInputPreference -> genInputPref(item)
                        is SimpleListPreference -> genListPref(item)
                        is SimpleMSListPreference -> genMSListPref(item)
                        is SimpleDropDownPreference -> genDropDownPref(item)
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

    private fun configureLibsPreference() {
        val libsPref = findPreference<Preference>("libs")
        libsPref?.onPreferenceClickListener = LibsClickListener(requireContext())
    }

    private fun configureMSListPreferences() {
        if (config.enableMSListPreferenceSummaryProvider) {
            for (i in 0 until preferenceScreen.preferenceCount) {
                val pref = preferenceScreen.getPreference(i)
                if (pref is MultiSelectListPreference) {
                    pref.summaryProvider = SimpleMSListPreferenceSummaryProvider()
                } else if (pref is PreferenceCategory) {
                    // Preference is a Category -> search for children
                    for (j in 0 until pref.preferenceCount) {
                        val childPref = pref.getPreference(j)
                        if (childPref is MultiSelectListPreference)
                            childPref.summaryProvider = SimpleMSListPreferenceSummaryProvider()
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

    private fun genCheckboxPref(sp: SimpleCheckboxPreference) = CheckBoxPreference(context).apply {
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

    private fun genListPref(sp: SimpleListPreference) = ListPreference(context).apply {
        initializeGeneralAttributes(sp, this)
        dialogTitle = if(sp.dialogTitle.isNotEmpty()) sp.dialogTitle else sp.title
        dialogMessage = sp.dialogMessage
        if(sp.dialogIcon != null) {
            dialogIcon = sp.dialogIcon
        } else if(sp.dialogIconRes != 0) {
            setDialogIcon(sp.dialogIconRes)
        }
        if(sp.dialogLayoutRes != 0) dialogLayoutResource = sp.dialogLayoutRes
        if(sp.simpleSummaryProvider) summary = SimplePreference.SUMMARY_VALUE
        entries = sp.entries.toTypedArray()
        entryValues = (sp.entries.indices).map { it.toString() }.toTypedArray()
        setDefaultValue(sp.defaultIndex.toString())
    }

    private fun genMSListPref(sp: SimpleMSListPreference) = MultiSelectListPreference(context).apply {
        initializeGeneralAttributes(sp, this)
        dialogTitle = if(sp.dialogTitle.isNotEmpty()) sp.dialogTitle else sp.title
        dialogMessage = sp.dialogMessage
        if(sp.dialogIcon != null) {
            dialogIcon = sp.dialogIcon
        } else if(sp.dialogIconRes != 0) {
            setDialogIcon(sp.dialogIconRes)
        }
        if(sp.dialogLayoutRes != 0) dialogLayoutResource = sp.dialogLayoutRes
        if(sp.simpleSummaryProvider) summaryProvider = SimpleMSListPreferenceSummaryProvider()
        entries = sp.entries.toTypedArray()
        entryValues = (sp.entries.indices).map { it.toString() }.toTypedArray()
        setDefaultValue(sp.defaultIndex)
    }

    private fun genDropDownPref(sp: SimpleDropDownPreference) = DropDownPreference(context).apply {
        initializeGeneralAttributes(sp, this)
        if(sp.simpleSummaryProvider) summary = SimplePreference.SUMMARY_VALUE
        entries = sp.entries.toTypedArray()
        entryValues = (sp.entries.indices).map { it.toString() }.toTypedArray()
        setDefaultValue(sp.defaultIndex.toString())
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

    // -------------------------------------- Utility methods --------------------------------------

    private fun initializeGeneralAttributes(sp: SimplePreference, pref: Preference) {
        pref.apply {
            key = if(sp.key.isBlank()) sp.title.toCamelCase() else sp.key
            title = sp.title
            summary = sp.summary
            isEnabled = sp.enabled
            if(sp.icon != 0) setIcon(sp.icon)
            isIconSpaceReserved = sp.iconSpaceReserved
            onPreferenceClickListener = sp.onClick
        }
    }
}