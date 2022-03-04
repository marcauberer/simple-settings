/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.exception

/**
 * Thrown, if something with the settings reset went wrong. Likely due to an xml configuration
 * mistake. Please check especially the defined keys for mistakes.
 */
class SettingsResetException: RuntimeException("Something went wrong when resetting the settings." +
        "Please check if all keys are configured correctly")