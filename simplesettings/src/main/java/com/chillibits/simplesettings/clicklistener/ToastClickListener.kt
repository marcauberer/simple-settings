/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.clicklistener

import android.content.Context
import android.widget.Toast
import androidx.preference.Preference

class ToastClickListener(
    private val context: Context,
    private val message: String,
    private val duration: Int = Toast.LENGTH_SHORT
): Preference.OnPreferenceClickListener {
    override fun onPreferenceClick(preference: Preference): Boolean {
        Toast.makeText(context, message, duration).show()
        return true
    }
}