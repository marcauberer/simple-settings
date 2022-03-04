/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.clicklistener

import android.content.Context
import androidx.preference.Preference
import com.mikepenz.aboutlibraries.LibsBuilder

class LibsClickListener(
    private val context: Context
): Preference.OnPreferenceClickListener {
    override fun onPreferenceClick(preference: Preference): Boolean {
        LibsBuilder().start(context)
        return true
    }
}