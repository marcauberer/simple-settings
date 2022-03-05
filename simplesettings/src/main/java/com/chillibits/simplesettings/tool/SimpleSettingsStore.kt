/*
 * Copyright © Marc Auberer 2020-2022. All rights reserved
 */

package com.chillibits.simplesettings.tool

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import me.ibrahimsn.library.LiveSharedPreferences

// ----------------------------------- Normal preferences -----------------------------------

/**
 * Returns the value of a string preference
 *
 * @param name Name of the preference
 * @param default Default value, if the pref does not exist yet
 */
fun Context.getPrefStringValue(name: String, default: String = "") =
        getPrefs().getString(name, default) ?: default

/**
 * Returns the value of a int preference
 *
 * @param name Name of the preference
 * @param default Default value, if the pref does not exist yet
 */
fun Context.getPrefIntValue(name: String, default: Int = 0) =
        getPrefs().getInt(name, default)

/**
 * Returns the value of a boolean preference
 *
 * @param name Name of the preference
 * @param default Default value, if the pref does not exist yet
 */
fun Context.getPrefBooleanValue(name: String, default: Boolean = false) =
        getPrefs().getBoolean(name, default)

/**
 * Returns the value of a float preference
 *
 * @param name Name of the preference
 * @param default Default value, if the pref does not exist yet
 */
fun Context.getPrefFloatValue(name: String, default: Float = 0f) =
        getPrefs().getFloat(name, default)

/**
 * Returns the value of a long preference
 *
 * @param name Name of the preference
 * @param default Default value, if the pref does not exist yet
 */
fun Context.getPrefLongValue(name: String, default: Long = 0) =
        getPrefs().getLong(name, default)

/**
 * Returns the value of a string set preference
 *
 * @param name Name of the preference
 * @param default Default value, if the pref does not exist yet
 */
fun Context.getPrefStringSetValue(name: String, default: Set<String> = emptySet()) =
        getPrefs().getStringSet(name, default) ?: default

// ------------------------------------ Live preferences ------------------------------------

/**
 * Returns an observer for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 */
fun LifecycleOwner.getPrefObserver(
        context: Context,
        name: String,
        observer: Observer<String>,
        default: String = ""
) = LiveSharedPreferences(context.getPrefs()).getString(name, default).observe(this, observer)

/**
 * Returns an observer for a preference value
 *
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData Observer instance
 */
fun Context.getPrefObserverForViewModel(
        name: String,
        observer: Observer<String>,
        default: String = ""
) = LiveSharedPreferences(getPrefs()).getString(name, default).apply {
    observeForever(observer)
}

/**
 * Returns a LiveData object for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData instance
 */
fun getPreferenceLiveData(
        context: Context,
        name: String,
        default: String = ""
) = LiveSharedPreferences(context.getPrefs()).getString(name, default)



/**
 * Returns an observer for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 */
fun LifecycleOwner.getPrefObserver(
        context: Context,
        name: String,
        observer: Observer<Int>,
        default: Int = 0
) = LiveSharedPreferences(context.getPrefs()).getInt(name, default).observe(this, observer)

/**
 * Returns an observer for a preference value
 *
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData Observer instance
 */
fun Context.getPrefObserverForViewModel(
        name: String,
        observer: Observer<Int>,
        default: Int = 0
) = LiveSharedPreferences(getPrefs()).getInt(name, default).apply {
    observeForever(observer)
}

/**
 * Returns a LiveData object for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData instance
 */
fun getPreferenceLiveData(
        context: Context,
        name: String,
        default: Int = 0
) = LiveSharedPreferences(context.getPrefs()).getInt(name, default)



/**
 * Returns an observer for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 */
fun LifecycleOwner.getPrefObserver(
        context: Context,
        name: String,
        observer: Observer<Boolean>,
        default: Boolean = false
) = LiveSharedPreferences(context.getPrefs()).getBoolean(name, default).observe(this, observer)

/**
 * Returns an observer for a preference value
 *
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData Observer instance
 */
fun Context.getPrefObserverForViewModel(
        name: String,
        observer: Observer<Boolean>,
        default: Boolean = false
) = LiveSharedPreferences(getPrefs()).getBoolean(name, default).apply {
    observeForever(observer)
}

/**
 * Returns a LiveData object for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData instance
 */
fun getPreferenceLiveData(
        context: Context,
        name: String,
        default: Boolean = false
) = LiveSharedPreferences(context.getPrefs()).getBoolean(name, default)



/**
 * Returns an observer for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 */
fun LifecycleOwner.getPrefObserver(
        context: Context,
        name: String,
        observer: Observer<Float>,
        default: Float = 0f
) = LiveSharedPreferences(context.getPrefs()).getFloat(name, default).observe(this, observer)

/**
 * Returns an observer for a preference value
 *
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData Observer instance
 */
fun Context.getPrefObserverForViewModel(
        name: String,
        observer: Observer<Float>,
        default: Float = 0f
) = LiveSharedPreferences(getPrefs()).getFloat(name, default).apply {
    observeForever(observer)
}

/**
 * Returns a LiveData object for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData instance
 */
fun getPreferenceLiveData(
        context: Context,
        name: String,
        default: Float = 0f
) = LiveSharedPreferences(context.getPrefs()).getFloat(name, default)



/**
 * Returns an observer for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 */
fun LifecycleOwner.getPrefObserver(
        context: Context,
        name: String,
        observer: Observer<Long>,
        default: Long = 0
) = LiveSharedPreferences(context.getPrefs()).getLong(name, default).observe(this, observer)

/**
 * Returns an observer for a preference value
 *
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData instance
 */
fun Context.getPrefObserver(
        name: String,
        observer: Observer<Long>,
        default: Long = 0
) = LiveSharedPreferences(getPrefs()).getLong(name, default).apply {
    observeForever(observer)
}

/**
 * Returns a LiveData object for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData instance
 */
fun getPreferenceLiveData(
        context: Context,
        name: String,
        default: Long = 0
) = LiveSharedPreferences(context.getPrefs()).getLong(name, default)



/**
 * Returns an observer for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 */
fun LifecycleOwner.getPrefObserver(
        context: Context,
        name: String,
        observer: Observer<Set<String>>,
        default: Set<String> = emptySet()
) = LiveSharedPreferences(context.getPrefs()).getStringSet(name, default).observe(this, observer)

/**
 * Returns an observer for a preference value
 *
 * @param name Name of the observed preference
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData Observer instance
 */
fun Context.getPrefObserverForViewModel(
        name: String,
        observer: Observer<Set<String>>,
        default: Set<String> = emptySet()
) = LiveSharedPreferences(getPrefs()).getStringSet(name, default).apply {
    observeForever(observer)
}

/**
 * Returns a LiveData object for a preference value
 *
 * @param context Context for creating instance of SharedPreferences
 * @param name Name of the observed preference
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData instance
 */
fun getPreferenceLiveData(
        context: Context,
        name: String,
        default: Set<String> = emptySet()
) = LiveSharedPreferences(context.getPrefs()).getStringSet(name, default)

// -------------------------------- Multiple live preferences -------------------------------

/**
 * Returns an observer for multiple values
 *
 * @param context Context for creating instance of SharedPreferences
 * @param names List of names of the observed preferences
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 */
fun <T> LifecycleOwner.getMultiplePrefObserver(
        context: Context,
        names: List<String>,
        observer: Observer<Map<String, T?>>,
        default: T
) = LiveSharedPreferences(context.getPrefs()).listenMultiple(names, default).observe(this, observer)

/**
 * Returns an observer for multiple values
 *
 * @param names List of names of the observed preferences
 * @param observer Observer, which fires the value on every update
 * @param default Default value, if the pref does not exist yet
 *
 * @return LiveData Observer instance
 */
fun <T> Context.getMultiplePrefObserver(
        names: List<String>,
        observer: Observer<Map<String, T?>>,
        default: T
) = LiveSharedPreferences(getPrefs()).listenMultiple(names, default).apply {
    observeForever(observer)
}

// L