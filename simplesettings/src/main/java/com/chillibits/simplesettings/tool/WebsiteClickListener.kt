package com.chillibits.simplesettings.tool

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.chillibits.simplesettings.item.SimplePreference

class WebsiteClickListener(
    private val context: Context,
    private val url: String
): SimplePreference.OnPreferenceClickListener {
    override fun onClick(pref: SimplePreference) {
        context.startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }
}