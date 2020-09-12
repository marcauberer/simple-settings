/*
 * Copyright © Marc Auberer 2020. All rights reserved
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

    override fun onPreferenceClick(preference: Preference?): Boolean {
        val context = preference!!.context

        // Apply type presets
        when(type) {
            Type.OK -> positiveButtonText = context.getString(R.string.ok)
            Type.YES_NO -> {
                positiveButtonText = context.getString(R.string.yes)
                negativeButtonText = context.getString(R.string.no)
            }
            Type.YES_CANCEL -> {
                positiveButtonText = context.getString(R.string.yes)
                negativeButtonText = context.getString(R.string.cancel)
            }
            Type.YES_NO_CANCEL -> {
                positiveButtonText = context.getString(R.string.yes)
                negativeButtonText = context.getString(R.string.no)
                neutralButtonText = context.getString(R.string.cancel)
            }
        }

        dialog = AlertDialog.Builder(context)
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