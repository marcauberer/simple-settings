# Simple Settings Activity
[![Download](https://api.bintray.com/packages/marcauberer/simplesettings/com.chillibits%3Asimplesettings/images/download.svg)](https://bintray.com/marcauberer/simplesettings/com.chillibits%3Asimplesettings/_latestVersion)
![Android CI](https://github.com/marcauberer/simple-settings/workflows/Android%20CI/badge.svg)
[![API](https://img.shields.io/badge/API-21%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Article on Medium](https://aleen42.github.io/badges/src/medium.svg)](https://medium.com/swlh/simple-settings-library-build-a-settings-screen-in-seconds-5b6394fbd2fc)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

Simple Settings is a library, which provides a simple to use, lightweight solution to create a settings screen without any boilerplate code. This behaviour saves cost, time and effort.

## Screenshots (Android 10)
<img src="https://github.com/marcauberer/simple-settings/raw/master/media/screenshot1.png" width="275" title="Screenshot 1"> <img src="https://github.com/marcauberer/simple-settings/raw/master/media/screenshot2.png" width="275" title="Screenshot 2"> <img src="https://github.com/marcauberer/simple-settings/raw/master/media/screenshot3.png" width="275" title="Screenshot 3">

## Try it
If you want to test the library, please visit the sample app on [Google Play](https://play.google.com/store/apps/details?id=com.chillibits.simplesettingssample)!

## Usage
The first step for using this library is, to add it to the dependency section in your project:
```gradle
implementation 'com.chillibits:simplesettings:1.0.0-alpha09'

// Required dependencies
implementation 'com.google.android.material:material:<latest-version>'
implementation 'androidx.preference:preference:<latest-version>'
```

You also have to register the activity in your manifest:
```xml
<activity
    android:name="com.chillibits.simplesettings.ui.SimpleSettingsActivity"
    android:theme="@style/Theme.MaterialComponents.DayNight.NoActionBar" />
```

The library accepts two different ways, for providing the settings screen information.

### Provide items programmatically
You can create the settings items, by using the `show()` method with the callback like this:
```kotlin
SimpleSettings(this).show {
    Section {
        title = "Test section"
        for (i in 1..4) {
            SwitchPref {
                title = "Test 1.$i"
                summary = "This is a Test 1.$i"
                defaultValue = if(i % 2 == 0) SimpleSwitchPreference.ON else SimpleSwitchPreference.OFF
            }
        }
        if(true) {
            TextPref {
                title = "Test 2"
                summary = "This is a Test 2"
            }
        }
    }
    Section {
        InputPref {
            title = "Test 3"
            summary = "This is a Test 3"
        }
    }
}
```
This is especially useful, when you need to generate your preferences at runtime. You can use loops and conditions as you can see above.

You can optionally pass an object of `SimpleSettingsConfig` to the constructor of your `SimpleSettings` instance, to customize the appearance of the settings activity. The different customization options are listed [below](#customization).

### Provide items with xml file
You also can specify your preference screen [as an usual xml file](https://developer.android.com/guide/topics/ui/settings#create_a_hierarchy):
```xml
<PreferenceScreen
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <PreferenceCategory
        app:key="preference_category"
        app:title="Preference category">

        <Preference
            app:key="preference_test"
            app:title="Test"
            app:summary="This is a test preference"/>

        <SwitchPreferenceCompat
            app:key="test"
            app:defaultValue="true"
            app:title="Test"
            app:summary="This is a test"/>
    </PreferenceCategory>
</PreferenceScreen>
```

to build the settings screen in its default configuration, you can simply call the `show` method of SimpleSettings and pass the prepared xml resource file to it.

```kotlin
SimpleSettings(this@MainActivity).show(R.xml.preferences)
```

or (if you need to add PreferenceClickListeners to the preference items)

```kotlin
private fun showPreferences() {
    val config = SimpleSettingsConfig().apply {
        showResetOption = true
        preferenceCallback = this@MainActivity
    }
    SimpleSettings(this@MainActivity, config).show(R.xml.preferences)
}

override fun onPreferenceClick(context: Context, key: String): Preference.OnPreferenceClickListener? {
    return when(key) {
        "preference" -> WebsiteClickListener(this@MainActivity, getString(R.string.url_github))
        "custom-preference" -> Preference.OnPreferenceClickListener {

            true
        }

        else -> super.onPreferenceClick(context, key)
    }
}
```

## Customization
The library offers a few customization options. For applying those options, you have to pass an object of `SimpleSettingsConfig` to the constructor of your `SimpleSettings` instance.

| Method                            | Description                                                                                                                                    |
|-----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| `setActivityTitle(String)`        | Sets the toolbar title for the SettingsActivity. The default value is 'Settings', translated to all supported languages                        |
| `setActivityTitle(Context, Int)`  | Sets the toolbar title for the SettingsActivity with a string resource. The default value is 'Settings', translated to all supported languages |
| `displayHomeAsUpEnabled(Boolean)` | Enables or disables the arrow icon in the top left corner of the activity to return to the calling activity. Default is `true`                 |
| `showResetOption(Boolean)`        | Enables or disables an options menu item for resetting all preferences to the default values. Default is `false`                               |
| `setPreferenceCallback(Context)`  | Sets a callback for subscribing to click events of preference items                                                                            |

If you miss a customization option, please let us know, by opening an issue.

## Predefined click listeners
The library offers a few predefined click listeners to save you lots of boilerplate code. These click listeners are available:

-   DialogClickListener ([more information](https://github.com/marcauberer/simple-settings/wiki/DialogClickListener))
-   LibsClickListener ([more information](https://github.com/marcauberer/simple-settings/wiki/LibsClickListener))
-   PlayStoreClickListener ([more information](https://github.com/marcauberer/simple-settings/wiki/PlayStoreClickListener))
-   ToastClickListener ([more information](https://github.com/marcauberer/simple-settings/wiki/ToastClickListener))
-   WebsiteClickListener ([more information](https://github.com/marcauberer/simple-settings/wiki/WebsiteClickListener))

## Dive in deeper into the project
Please visit [the wiki](https://github.com/marcauberer/simple-settings/wiki), if you want to understand the perks of this library or if you want to learn more about certain preference types.

## Supported languages
Here are the currently supported languages for this library.

-   English
-   German

New translations are highly appreciated. If you want to translate the lib, please open a pr.

## Contribute to the project
If you want to contribute to this project, please feel free to send us a pull request.

## Credits
Thanks to all contributors and translators!

© Marc Auberer 2020
