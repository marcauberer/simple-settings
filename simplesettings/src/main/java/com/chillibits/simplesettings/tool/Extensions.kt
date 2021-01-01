/*
 * Copyright © Marc Auberer 2020-2021. All rights reserved
 */

package com.chillibits.simplesettings.tool

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.preference.PreferenceManager
import java.util.*

fun String.toCamelCase() = split(" ")
    .joinToString("") {
        it.toLowerCase(Locale.getDefault()).capitalize()
    }.decapitalize()

fun Context.getPrefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

fun Context.openGooglePlayAppSite() {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}