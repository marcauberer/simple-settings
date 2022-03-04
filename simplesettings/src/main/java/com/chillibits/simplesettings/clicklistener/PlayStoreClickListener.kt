/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.clicklistener

import android.content.Context
import androidx.preference.Preference
import com.chillibits.simplesettings.tool.openGooglePlayAppSite

class PlayStoreClickListener(
    private val context: Context
): Preference.OnPreferenceClickListener {
    override fun onPreferenceClick(preference: Preference): Boolean {
        context.openGooglePlayAppSite()
        return true
    }
}