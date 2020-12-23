/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.core

import android.content.Context

class PreferencePage(
    private val context: Context
) {

    // Attributes
    var title = ""
    var enabled = true
    var items = ArrayList<PreferenceSection>()


}