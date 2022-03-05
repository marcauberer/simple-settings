/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.item

import android.content.Context
import androidx.annotation.StringRes
import com.chillibits.simplesettings.R
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.LibsActivity
import java.util.*

class SimpleLibsPreference(
    private val context: Context,
    iconSpaceReservedByDefault: Boolean
): SimplePreference(context, iconSpaceReservedByDefault) {

    // Attributes
    var activityTitle: String? = null
    @StringRes var activityTitleRes = 0
        set(value) { activityTitle = context.getString(value) }

    var edgeToEdge = false

    var aboutAppName: String? = null
    @StringRes var aboutAppNameRes = 0
        set(value) { aboutAppName = context.getString(value) }

    var aboutAppSpecial1: String? = null
    @StringRes var aboutAppSpecial1Res = 0
        set(value) { aboutAppSpecial1 = context.getString(value) }

    var aboutAppSpecial1Description: String? = null
    @StringRes var aboutAppSpecial1DescriptionRes = 0
        set(value) { aboutAppSpecial1Description = context.getString(value) }

    var aboutAppSpecial2: String? = null
    @StringRes var aboutAppSpecial2Res = 0
        set(value) { aboutAppSpecial2 = context.getString(value) }

    var aboutAppSpecial2Description: String? = null
    @StringRes var aboutAppSpecial2DescriptionRes = 0
        set(value) { aboutAppSpecial2Description = context.getString(value) }

    var aboutAppSpecial3: String? = null
    @StringRes var aboutAppSpecial3Res = 0
        set(value) { aboutAppSpecial3 = context.getString(value) }

    var aboutAppSpecial3Description: String? = null
    @StringRes var aboutAppSpecial3DescriptionRes = 0
        set(value) { aboutAppSpecial3Description = context.getString(value) }

    var aboutDescription: String? = null
    @StringRes var aboutDescriptionRes = 0
        set(value) { aboutDescription = context.getString(value) }

    var aboutMinimalDesign = false

    var aboutShowIcon = true

    var aboutShowVersion = true

    var aboutShowVersionCode = true

    var aboutShowVersionName = true

    var aboutVersionString = ""
    @StringRes var aboutVersionStringRes = 0
        set(value) { aboutVersionString = context.getString(value) }

    var autoDetect = true

    var checkCachedDetection = true

    var fields = emptyArray<String>()

    var internalLibraries = emptyArray<String>()

    var excludeLibraries = emptyArray<String>()

    var libraryComparator: Comparator<Library>? = null

    var ownLibsActivityClass: Class<*> = LibsActivity::class.java

    var showLicense = false

    var showLicenseDialog = true

    var showLoadingProgress = true

    var showVersion = true

    var sort = true

    init {
        title = context.getString(R.string.libsPreferenceTitle)
        summary = context.getString(R.string.libsPreferenceSummary)
    }
}