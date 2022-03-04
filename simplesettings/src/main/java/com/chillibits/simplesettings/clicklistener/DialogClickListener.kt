/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.clicklistener

import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import com.chillibits.simplesettings.R

class DialogClickListener(
    private val callback: DialogResultCallback?
): Preference.OnPreferenceClickListener {

    // Attributes
    private lateinit var dialog: AlertDialog
    var type: Type? = null
    var title: String? = null
    var message: String? = null
    var negativeButtonText: String? = null
    var positiveButtonText: String? = null
    var neutralButtonText: String? = null
    var cancelable = true
    var icon = 0

    // Enums
    enum class Type { OK, YES_NO, YES_CANCEL, YES_NO_CANCEL }
    enum class Button { POSITIVE, NEGATIVE, NEUTRAL }

    // Interfaces
    interface DialogResultCallback {
        fun onDialogButtonClicked(button: Button)
    }

    constructor(title: String, message: String, type: Type, callback: DialogResultCallback? = null) : this(callback) {
        this.title = title
        this.message = message
        this.type = type
    }

    constructor(callback: DialogResultCallback? = null, func: DialogClickListener.() -> Unit) : this(callback) { func() }

    override fun onPreferenceClick(preference: Preference): Boolean {
        // Apply type presets
        when(type) {
            Type.YES_NO -> {
                positiveButtonText = preference.context.getString(R.string.yes)
                negativeButtonText = preference.context.getString(R.string.no)
            }
            Type.YES_CANCEL -> {
                positiveButtonText = preference.context.getString(R.string.yes)
                negativeButtonText = preference.context.getString(R.string.cancel)
            }
            Type.YES_NO_CANCEL -> {
                positiveButtonText = preference.context.getString(R.string.yes)
                negativeButtonText = preference.context.getString(R.string.no)
                neutralButtonText = preference.context.getString(R.string.cancel)
            }
            else -> preference.context.getString(R.string.ok)
        }

        dialog = AlertDialog.Builder(preference.context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(cancelable)
            .setIcon(icon)
            .setPositiveButton(positiveButtonText) { _, _ -> callback?.onDialogButtonClicked(Button.POSITIVE) }
            .setNegativeButton(negativeButtonText) { _, _ -> callback?.onDialogButtonClicked(Button.NEGATIVE) }
            .setNeutralButton(neutralButtonText) { _, _ -> callback?.onDialogButtonClicked(Button.NEUTRAL) }
            .show()
        return true
    }
}