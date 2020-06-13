/*
 * Copyright © Marc Auberer 2020. All rights reserved
 */

package com.chillibits.simplesettings.exception

class SettingsResetException: RuntimeException("Something went wrong when resetting the settings." +
        "Please check if all keys are configured correctly")