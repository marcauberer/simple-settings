/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.ui

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.preference.*
import com.chillibits.simplesettings.core.SimpleSettings
import com.chillibits.simplesettings.core.SimpleSettingsConfig
import com.chillibits.simplesettings.core.elements.PreferenceHeader
import com.chillibits.simplesettings.core.elements.PreferencePage
import com.chillibits.simplesettings.core.elements.PreferenceSection
import com.chillibits.simplesettings.item.*
import com.chillibits.simplesettings.tool.SimpleMSListPreferenceSummaryProvider
import com.chillibits.simplesettings.tool.toCamelCase
import com.mikepenz.aboutlibraries.LibsBuilder
import com.skydoves.colorpickerpreference.ColorPickerPreference

/**
 * The SimpleSettingsFragment is embedded in the SimpleSettingsActivity and displays the actual
 * preferences.
 */
internal class SimpleSettingsFragment : PreferenceFragmentCompat() {

    // Variables as objects
    private val preferenceRes = SimpleSettings.preferenceRes
    private val sections = SimpleSettings.sections
    private val config = SimpleSettings.config
    private val preferenceCallback = SimpleSettings.config.preferenceCallback

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if(preferenceRes != 0) {
            // Inflate preferences from xml resource
            setPreferencesFromResource(preferenceRes, rootKey)

            // Connect callback methods
            connectCallbackMethods()
        } else {
            // Build preferences from sections array
            preferenceScreen = preferenceManager.createPreferenceScreen(requireContext())

            // Add sections
            sections.filterIsInstance(PreferenceSection::class.java).forEach { section ->
                // Add category itself
                val category = PreferenceCategory(requireContext()).apply {
                    title = section.title
                    isEnabled = section.enabled
                    isIconSpaceReserved = section.iconSpaceReserved
                }
                preferenceScreen.addPreference(category)

                // Add items to category
                section.items.forEach { item ->
                    val preferenceItem = when(item) {
                        is PreferencePage -> genPagePref(item)
                        is SimpleTextPreference -> genTextPref(item)
                        is SimpleSwitchPreference -> genSwitchPref(item)
                        is SimpleCheckboxPreference -> genCheckboxPref(item)
                        is SimpleInputPreference -> genInputPref(item)
                        is SimpleListPreference -> genListPref(item)
                        is SimpleMSListPreference -> genMSListPref(item)
                        is SimpleDropDownPreference -> genDropDownPref(item)
                        is SimpleSeekBarPreference -> genSeekBarPref(item)
                        is SimpleLibsPreference -> genLibsPref(item)
                        is SimpleColorPreference -> genColorPref(item)
                        else -> Preference(requireContext())
                    }
                    category.addPreference(preferenceItem)
                }
            }

            // Post processing
            sections.filterIsInstance(PreferenceSection::class.java).forEach {
                it.items.forEach { item ->
                    // Setup dependencies
                    if(item.dependency.isNotBlank()) {
                        val key = if(item.key.isBlank()) item.title.toCamelCase() else item.key
                        val pref = findPreference<Preference>(key)
                        pref?.dependency = item.dependency
                    }
                }
            }
        }
    }

    private fun connectCallbackMethods() {
        for (i in 0 until preferenceScreen.preferenceCount) {
            val pref = preferenceScreen.getPreference(i)
            if(pref is PreferenceCategory) {
                // Preference is a Category -> search for children
                for (j in 0 until pref.preferenceCount) {
                    val childPref = pref.getPreference(j)
                    setActionListenerToPreference(childPref)
                    if (childPref is MultiSelectListPreference && config.enableMSListPreferenceSummaryProvider)
                        childPref.summaryProvider = SimpleMSListPreferenceSummaryProvider()
                }
            } else {
                setActionListenerToPreference(pref)
                if (pref is MultiSelectListPreference && config.enableMSListPreferenceSummaryProvider)
                    pref.summaryProvider = SimpleMSListPreferenceSummaryProvider()
            }
        }
    }

    private fun setActionListenerToPreference(pref: Preference) {
        pref.setOnPreferenceClickListener {
            preferenceCallback?.onPreferenceAction(
                    requireContext(),
                    pref.key,
                    SimpleSettingsConfig.PreferenceAction.CLICK
            )
            preferenceCallback?.onPreferenceClick(requireContext(), pref.key)?.onPreferenceClick(
                    pref
            )
            true
        }
    }

    // -------------------------------- Preference creator methods ---------------------------------

    private fun genPagePref(sp: PreferencePage) = Preference(requireContext()).apply {
        initializeGeneralAttributes(sp, this)
        // Override click listener to open cascading activity
        sp.onClick = Preference.OnPreferenceClickListener {
            // Initialize new instance of library with the regarding subset of sections
            val subConfig = SimpleSettingsConfig.Builder()
                .setActivityTitle(sp.activityTitle.ifBlank { sp.title })
                .showResetOption(config.showResetOption)
                .setIconSpaceReservedByDefault(isIconSpaceReserved)
                .displayHomeAsUpEnabled(sp.displayHomeAsUpEnabled)
                .build()

            // Launch nested settings screen
            SimpleSettings(context, subConfig).show {
                sp.subSections.filterIsInstance(PreferenceHeader::class.java).forEach {
                    Header {
                        layoutResource = it.layoutResource
                    }
                }
                sp.subSections.filterIsInstance(PreferenceSection::class.java).forEach {
                    Section {
                        title = it.title
                        enabled = it.enabled
                        items.addAll(it.items)
                    }
                }
            }
            true
        }
    }

    private fun genTextPref(sp: SimpleTextPreference) = Preference(requireContext()).apply {
        initializeGeneralAttributes(sp, this)
    }

    private fun genSwitchPref(sp: SimpleSwitchPreference) = SwitchPreferenceCompat(requireContext()).apply {
        initializeGeneralAttributes(sp, this)
        if(sp.summaryOff.isNotEmpty() && sp.summaryOn.isNotEmpty()) {
            summaryOff = sp.summaryOff
            summaryOn = sp.summaryOn
        }
        setDefaultValue(sp.defaultValue)
    }

    private fun genCheckboxPref(sp: SimpleCheckboxPreference) = CheckBoxPreference(requireContext()).apply {
        initializeGeneralAttributes(sp, this)
        if(sp.summaryOff.isNotEmpty() && sp.summaryOn.isNotEmpty()) {
            summaryOff = sp.summaryOff
            summaryOn = sp.summaryOn
        }
        setDefaultValue(sp.defaultValue)
    }

    private fun genInputPref(sp: SimpleInputPreference) = EditTextPreference(requireContext()).apply {
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

    private fun genListPref(sp: SimpleListPreference) = ListPreference(requireContext()).apply {
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

    private fun genMSListPref(sp: SimpleMSListPreference) = MultiSelectListPreference(requireContext()).apply {
        initializeGeneralAttributes(sp, this)
        dialogTitle = if(sp.dialogTitle.isNotEmpty()) sp.dialogTitle else sp.title
        dialogMessage = sp.dialogMessage
        if(sp.dialogIcon != null) {
            dialogIcon = sp.dialogIcon
        } else if(sp.dialogIconRes != 0) {
            setDialogIcon(sp.dialogIconRes)
        }
        if(sp.dialogLayoutRes != 0) dialogLayoutResource = sp.dialogLayoutRes
        if(sp.simpleSummaryProvider) summaryProvider =
            SimpleMSListPreferenceSummaryProvider()
        entries = sp.entries.toTypedArray()
        entryValues = (sp.entries.indices).map { it.toString() }.toTypedArray()
        setDefaultValue(sp.defaultIndex)
    }

    private fun genDropDownPref(sp: SimpleDropDownPreference) = DropDownPreference(requireContext()).apply {
        initializeGeneralAttributes(sp, this)
        if(sp.simpleSummaryProvider) summary = SimplePreference.SUMMARY_VALUE
        entries = sp.entries.toTypedArray()
        entryValues = (sp.entries.indices).map { it.toString() }.toTypedArray()
        setDefaultValue(sp.defaultIndex.toString())
    }

    private fun genSeekBarPref(sp: SimpleSeekBarPreference) = SeekBarPreference(requireContext()).apply {
        initializeGeneralAttributes(sp, this)
        min = sp.min
        max = sp.max
        showSeekBarValue = sp.showValue
        setDefaultValue(sp.defaultValue)
    }

    private fun genLibsPref(sp: SimpleLibsPreference) = Preference(requireContext()).apply {
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

    private fun genColorPref(sp: SimpleColorPreference): ColorPickerPreference {
        /*val attrs: AttributeSet = object : AttributeSet {
            override fun getAttributeCount() = 0
            override fun getAttributeName(index: Int) = null
            override fun getAttributeValue(index: Int) = null
            override fun getAttributeValue(namespace: String, name: String) = null
            override fun getPositionDescription() = null
            override fun getAttributeNameResource(index: Int) = 0
            override fun getAttributeListValue(namespace: String, attribute: String, options: Array<String>, defaultValue: Int) = 0
            override fun getAttributeBooleanValue(namespace: String, attribute: String, defaultValue: Boolean) = false
            override fun getAttributeResourceValue(namespace: String, attribute: String, defaultValue: Int) = 0
            override fun getAttributeIntValue(namespace: String, attribute: String, defaultValue: Int) = 0
            override fun getAttributeUnsignedIntValue(namespace: String, attribute: String, defaultValue: Int) = 0
            override fun getAttributeFloatValue(namespace: String, attribute: String, defaultValue: Float) = 0f
            override fun getAttributeListValue(index: Int, options: Array<String>, defaultValue: Int) = 0
            override fun getAttributeBooleanValue(index: Int, defaultValue: Boolean) = false
            override fun getAttributeResourceValue(index: Int, defaultValue: Int) = 0
            override fun getAttributeIntValue(index: Int, defaultValue: Int) = 0
            override fun getAttributeUnsignedIntValue(index: Int, defaultValue: Int) = 0
            override fun getAttributeFloatValue(index: Int, defaultValue: Float) = 0f
            override fun getIdAttribute() = null
            override fun getClassAttribute() = null
            override fun getIdAttributeResourceValue(defaultValue: Int) = 0
            override fun getStyleAttribute() = 0
        }*/

        return ColorPickerPreference(requireContext()).apply {
            initializeGeneralAttributes(sp, this)
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