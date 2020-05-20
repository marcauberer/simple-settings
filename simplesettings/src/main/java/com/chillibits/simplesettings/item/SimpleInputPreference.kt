package com.chillibits.simplesettings.item

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes

class SimpleInputPreference: SimplePreference() {
    // Attributes
    var defaultValue = ""
    var dialogTitle = ""
    var dialogMessage: String? = null
    @DrawableRes
    var dialogIconRes = 0
    var dialogIcon: Drawable? = null
    @LayoutRes
    var dialogLayoutRes = 0
}