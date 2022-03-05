/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.preference

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.preference.Preference
import com.chillibits.simplesettings.R
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.LibsActivity

class LibsPreference: Preference {

    // Attributes
    private var activityTitle: String? = null
    private var edgeToEdge = false
    private var aboutAppName: String? = null
    private var aboutAppSpecial1: String? = null
    private var aboutAppSpecial1Description: String? = null
    private var aboutAppSpecial2: String? = null
    private var aboutAppSpecial2Description: String? = null
    private var aboutAppSpecial3: String? = null
    private var aboutAppSpecial3Description: String? = null
    private var aboutDescription: String? = null
    private var aboutMinimalDesign = false
    private var aboutShowIcon = true
    private var aboutShowVersion = true
    private var aboutShowVersionCode = true
    private var aboutShowVersionName = true
    private var aboutVersionString = ""
    private var autoDetect = true
    private var checkCachedDetection = true
    private var fields: Array<String> = emptyArray()
    private var internalLibraries: Array<out String> = emptyArray()
    private var excludeLibraries: Array<out String> = emptyArray()
    private var libraryComparator: Comparator<Library>? = null
    private var ownLibsActivityClass: Class<*> = LibsActivity::class.java
    private var showLicense = false
    private var showLicenseDialog = true
    private var showLoadingProgress = true
    private var showVersion = true
    private var sort = true

    // Variables as objects
    private lateinit var libsBuilder: LibsBuilder

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        getAttrs(attrs)
        onInit()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int):
            super(context, attrs, defStyleAttr) {
        getAttrs(attrs, defStyleAttr)
        onInit()
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerPreference)
        try {
            setTypeArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerPreference,
                defStyle, 0)
        try {
            setTypeArray(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setTypeArray(typedArray: TypedArray) {
        activityTitle = typedArray.getString(R.styleable.LibsPreference_activity_title)
        edgeToEdge = typedArray.getBoolean(R.styleable.LibsPreference_edge_to_edge, edgeToEdge)
        aboutAppName = typedArray.getString(R.styleable.LibsPreference_about_app_name)
        aboutAppSpecial1 = typedArray.getString(R.styleable.LibsPreference_about_app_special1)
        aboutAppSpecial1Description = typedArray.getString(R.styleable.LibsPreference_about_app_special1_description)
        aboutAppSpecial2 = typedArray.getString(R.styleable.LibsPreference_about_app_special2)
        aboutAppSpecial2Description = typedArray.getString(R.styleable.LibsPreference_about_app_special2_description)
        aboutAppSpecial3 = typedArray.getString(R.styleable.LibsPreference_about_app_special3)
        aboutAppSpecial3Description = typedArray.getString(R.styleable.LibsPreference_about_app_special3_description)
        aboutDescription = typedArray.getString(R.styleable.LibsPreference_about_description)
        aboutMinimalDesign = typedArray.getBoolean(R.styleable.LibsPreference_about_minimal_design, aboutMinimalDesign)
        aboutShowIcon = typedArray.getBoolean(R.styleable.LibsPreference_about_show_icon, aboutShowIcon)
        aboutShowVersion = typedArray.getBoolean(R.styleable.LibsPreference_show_version, aboutShowVersion)
        aboutShowVersionCode = typedArray.getBoolean(R.styleable.LibsPreference_about_show_version_code, aboutShowVersionCode)
        aboutShowVersionName = typedArray.getBoolean(R.styleable.LibsPreference_about_show_version_name, aboutShowVersionName)
        aboutVersionString = typedArray.getString(R.styleable.LibsPreference_about_version_string).toString()
        autoDetect = typedArray.getBoolean(R.styleable.LibsPreference_auto_detect, autoDetect)
        checkCachedDetection = typedArray.getBoolean(R.styleable.LibsPreference_check_cached_detection, checkCachedDetection)
        showLicense = typedArray.getBoolean(R.styleable.LibsPreference_show_license, showLicense)
        showLicenseDialog = typedArray.getBoolean(R.styleable.LibsPreference_show_license_dialog, showLicenseDialog)
        showLoadingProgress = typedArray.getBoolean(R.styleable.LibsPreference_show_loading_progress, showLoadingProgress)
        showVersion = typedArray.getBoolean(R.styleable.LibsPreference_show_version, showVersion)
        sort = typedArray.getBoolean(R.styleable.LibsPreference_sort, sort)
    }

    private fun onInit() {
        libsBuilder = LibsBuilder().apply {
            activityTitle = this@LibsPreference.activityTitle
            edgeToEdge = this@LibsPreference.edgeToEdge
            aboutAppName = this@LibsPreference.aboutAppName ?: context.getString(R.string.app_name)
            aboutAppSpecial1 = this@LibsPreference.aboutAppSpecial1
            aboutAppSpecial1Description = this@LibsPreference.aboutAppSpecial1Description
            aboutAppSpecial2 = this@LibsPreference.aboutAppSpecial2
            aboutAppSpecial2Description = this@LibsPreference.aboutAppSpecial2Description
            aboutAppSpecial3 = this@LibsPreference.aboutAppSpecial3
            aboutAppSpecial3Description = this@LibsPreference.aboutAppSpecial3Description
            aboutDescription = this@LibsPreference.aboutDescription
            aboutMinimalDesign = this@LibsPreference.aboutMinimalDesign
            aboutShowIcon = this@LibsPreference.aboutShowIcon
            aboutShowVersion = this@LibsPreference.aboutShowVersion
            aboutShowVersionCode = this@LibsPreference.aboutShowVersionCode
            aboutShowVersionName = this@LibsPreference.aboutShowVersionName
            aboutVersionString = this@LibsPreference.aboutVersionString
            autoDetect = this@LibsPreference.autoDetect
            checkCachedDetection = this@LibsPreference.checkCachedDetection
            fields = this@LibsPreference.fields
            internalLibraries = this@LibsPreference.internalLibraries
            excludeLibraries = this@LibsPreference.excludeLibraries
            libraryComparator = this@LibsPreference.libraryComparator
            ownLibsActivityClass = this@LibsPreference.ownLibsActivityClass
            showLicense = this@LibsPreference.showLicense
            showLicenseDialog = this@LibsPreference.showLicenseDialog
            showLoadingProgress = this@LibsPreference.showLoadingProgress
            showVersion = this@LibsPreference.showVersion
            sort = this@LibsPreference.sort
        }
    }

    override fun onClick() {
        super.onClick()
        libsBuilder.start(context)
    }
}