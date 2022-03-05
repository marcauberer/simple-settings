/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.core.elements

import android.content.Context

/**
 * Base preference element, designed to be inherited from
 */
abstract class PreferenceElement(
        val context: Context,
        val iconSpaceReserved: Boolean
)