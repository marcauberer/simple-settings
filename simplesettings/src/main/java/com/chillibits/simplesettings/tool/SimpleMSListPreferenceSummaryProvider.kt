/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.tool

import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference

class SimpleMSListPreferenceSummaryProvider: Preference.SummaryProvider<MultiSelectListPreference> {
    override fun provideSummary(preference: MultiSelectListPreference): CharSequence {
        return preference.entries?.filterIndexed { index, _ ->
            preference.values.any { it == preference.entryValues[index].toString() }
        }?.joinToString(", ").toString()
    }
}