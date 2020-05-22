package com.chillibits.simplesettings.clicklistener

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.preference.Preference

class PlayStoreClickListener(
    private val context: Context
): Preference.OnPreferenceClickListener {
    override fun onPreferenceClick(preference: Preference?): Boolean {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + context.packageName)))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + context.packageName)))
        }
        return true
    }
}