/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.clicklistener

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.preference.Preference

class WebsiteClickListener(
    private val context: Context,
    private val url: String
): Preference.OnPreferenceClickListener {
    override fun onPreferenceClick(preference: Preference): Boolean {
        context.startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
        return true
    }
}