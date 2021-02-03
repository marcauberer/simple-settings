package com.chillibits.simplesettings.core

import android.content.Context

/**
 * Base preference element, designed to be inherited from
 */
abstract class PreferenceElement(
        val context: Context,
        val iconSpaceReserved: Boolean
)