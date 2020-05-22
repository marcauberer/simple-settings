package com.chillibits.simplesettings.item

import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.LibsActivity
import java.util.*

class SimpleLibsPreference: SimplePreference() {

    // Attributes
    var activityTitle: String? = null
    var edgeToEdge = false
    var aboutAppName: String? = null
    var aboutAppSpecial1: String? = null
    var aboutAppSpecial1Description: String? = null
    var aboutAppSpecial2: String? = null
    var aboutAppSpecial2Description: String? = null
    var aboutAppSpecial3: String? = null
    var aboutAppSpecial3Description: String? = null
    var aboutDescription: String? = null
    var aboutMinimalDesign = false
    var aboutShowIcon = true
    var aboutShowVersion = true
    var aboutShowVersionCode = true
    var aboutShowVersionName = true
    var aboutVersionString = ""
    var autoDetect = true
    var checkCachedDetection = true
    var fields: Array<String> = emptyArray()
    var internalLibraries: Array<out String> = emptyArray()
    var excludeLibraries: Array<out String> = emptyArray()
    var libraryComparator: Comparator<Library>? = null
    var ownLibsActivityClass: Class<*> = LibsActivity::class.java
    var showLicense = false
    var showLicenseDialog = true
    var showLoadingProgress = true
    var showVersion = true
    var sort = true
}