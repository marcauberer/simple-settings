/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.tool

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.preference.PreferenceManager

fun String.toCamelCase() = split(" ")
    .joinToString("") {
        it.lowercase().replaceFirstChar { char -> char.uppercase() }
    }.replaceFirstChar { char -> char.lowercase() }

fun Context.getPrefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

fun Context.openGooglePlayAppSite() {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}