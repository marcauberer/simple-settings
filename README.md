# Simple Settings Library for Android
[![Maven Central](https://img.shields.io/maven-central/v/com.chillibits/simplesettings.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.chillibits%22%20AND%20a:%22simplesettings%22)
![GitHub release](https://img.shields.io/github/v/release/marcauberer/simple-settings?include_prereleases)
![Android CI](https://github.com/marcauberer/simple-settings/workflows/Android%20CI/badge.svg)
[![API](https://img.shields.io/badge/API-21%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Article on Medium](https://aleen42.github.io/badges/src/medium.svg)](https://medium.com/swlh/simple-settings-library-build-a-settings-screen-in-seconds-5b6394fbd2fc)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

Simple Settings is a library, which provides a simple to use, lightweight solution to create a settings screen without any boilerplate code. This behaviour saves cost, time and effort.

## Try it
If you want to test the library, please visit the sample app on [Google Play](https://play.google.com/store/apps/details?id=com.chillibits.simplesettingssample)!

## Screenshots (Android 11)
<img src="https://github.com/marcauberer/simple-settings/raw/main/media/screenshots/screen1.png" width="205" title="Screenshot 1"> <img src="https://github.com/marcauberer/simple-settings/raw/main/media/screenshots/screen2.png" width="205" title="Screenshot 2"> <img src="https://github.com/marcauberer/simple-settings/raw/main/media/screenshots/screen3.png" width="205" title="Screenshot 3"> <img src="https://github.com/marcauberer/simple-settings/raw/main/media/screenshots/screen4.png" width="205" title="Screenshot 4">

## Usage
The first step for using this library is, to add it to the dependency section in your project:

Add repository to build.gradle file on project level:
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add dependencies to build.gradle file on module level (e.g. app/build.gradle):
```gradle
implementation 'com.chillibits:simplesettings:1.3.3'

// Required dependencies
implementation 'com.google.android.material:material:<latest-version>'
implementation 'androidx.preference:preference-ktx:<latest-version>'
```

The library accepts two different ways, for providing the settings screen information.

### Provide items programmatically
Depending on the complexity of your app, you can stick with a single-paged settings screen or you might choose a paged settings screen for more complex configurations.
#### Single-Paged configuration
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
        /*...*/
    }
    Section {
        InputPref {
            title = "Test 3"
            summary = "This is a Test 3"
        }
        /*...*/
    }
    /*...*/
}
```
This is especially useful, when you need to generate your preferences at runtime. You can use loops and conditions as you can see above.

*Note: If you want to pass a string / drawable / layout with its resource id, please use the properties with the `Res` suffix. For example:  `titleRes = R.string.app_name`.*

*Note: It is not mandatory to pass keys to each preference. In this cases, the library does auto-generate a key by converting the title of each preference to CamelCase.*<br>
**Examples**:
```
List Preference --> listPreference
ListPreference --> listpreference
This is a custom preference --> thisIsACustomPreference
custom --> custom
```

You can optionally pass an object of `SimpleSettingsConfig` to the constructor of your `SimpleSettings` instance, to customize the appearance of the settings activity. The different customization options are listed [below](#library-customization).

#### Paged Settings Screens
The library offers a solution for overloaded single-paged settings screens by supporting paged settings configurations.
```kotlin
SimpleSettings(this).show {
    Section {
        title = "Section"
        Page {
            title = "Page 1"
            summary = "Demo summary 1"
            displayHomeAsUpEnabled = false
            Section {
                title = "Demo subsection"
                TextPref {
                    title = "LibsClickListener"
                    onClick = LibsClickListener(this@MainActivity)
                }
                /*...*/
            }
        }
        Page {
            title = "Page 2"
            summary = "Demo summary 2"
            activityTitle = "Page 2.2"
            Section {
                DropDownPref {
                    title = "DropDownPreference"
                    simpleSummaryProvider = true
                    entries = listOf("Apple", "Banana", "Avocado", "Pineapple")
                }
                /*...*/
            }
        }
        /*...*/
    }
    Section {
        InputPref {
            title = "InputPreference"
            summary = "Click to set text"
            defaultValue = "Default text"
        }
        /*...*/
    }
    /*...*/ 
}
```
To learn more about the Page component, please visit its [wiki entry](https://github.com/marcauberer/simple-settings/wiki/PreferencePage).

#### Page Headers
The library supports page headers and allows you to pass layout resources to every page of your settings screen.
For more information and code samples, please visit the [corresponding wiki entry](https://github.com/marcauberer/simple-settings/wiki/PreferenceHeader).

### Provide items with xml file (only for single-paged configuration)
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
            
        <!-- ... -->
    </PreferenceCategory>
    <!-- ... -->
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

## Preference Types
-   SimpleTextPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleTextPreference))
-   SimpleSwitchPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleSwitchPreference))
-   SimpleInputPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleInputPreference))
-   SimpleListPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleListPreference))
-   SimpleMultiSelectListPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleMSListPreference))
-   SimpleCheckboxPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleCheckboxPreference))
-   SimpleDropDownPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleDropDownPreference))
-   SimpleSeekbarPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleSeekbarPreference))
-   SimpleLibsPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleLibsPreference))
-   SimpleColorPreference ([usage information](https://github.com/marcauberer/simple-settings/wiki/SimpleColorPreference))

## Retrieve preference values
### One-Time-Retrieval
You can either retrieve the values of the preferences as usual via the SharedPreferences or you can use the built-in shortcuts, coming with the library.
It provides extension functions for the `Context` class to easily access the preference values:
```kotlin
val value1 = getPrefStringValue("stringPreference", "default value")
val value2 = getPrefIntValue("intPreference", 99)
val value3 = getPrefBooleanValue("booleanPreference", true)
val value4 = getPrefFloatValue("floatPreference", 101.6f)
val value5 = getPrefLongValue("longPreference", 4834597833234)
val value6 = getPrefStringSetValue("stringSetPreference", setOf("Default 1", "Default 2"))
```
As you can see, this works for the types `String`, `Int`, `Boolean`, `Float`, `Long`, `StringSet`.

### Retrieval as LiveData
Furthermore, the library offers a feature to observe preference values as LiveData as follows:
```kotlin
val inputPref = getPreferenceLiveData(this@MainActivity, "listpreference")
```

or you can retrieve it directly as an LiveData Observer:
```kotlin
getPrefObserver(this@MainActivity, "listpreference", Observer<String> { value ->
    textField.text = value
})
```

Like above, this works for the types `String`, `Int`, `Boolean`, `Float`, `Long`, `StringSet`.<br>
*Note: This extension functions are only available for the classes, which implement `LifecycleOwner`.*

## Library customization
The library offers a few customization options. For applying those options, you have to pass an object of `SimpleSettingsConfig` to the constructor of your `SimpleSettings` instance.

| Method                            | Description                                                                                                                                                                                            |
|-----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `setActivityTitle(String)`        | Sets the toolbar title for the SettingsActivity. The default value is 'Settings', translated to all supported languages                                                                                |
| `setActivityTitle(Context, Int)`  | Sets the toolbar title for the SettingsActivity with a string resource. The default value is 'Settings', translated to all supported languages                                                         |
| `displayHomeAsUpEnabled(Boolean)` | Enables or disables the arrow icon in the top left corner of the activity to return to the calling activity. Default is `true`                                                                         |
| `showResetOption(Boolean)`        | Enables or disables an options menu item for resetting all preferences to the default values. Default is `false`                                                                                       |
| `setPreferenceCallback(Context)`  | Sets a callback for subscribing to click events of preference items                                                                                                                                    |
| `setPendingTransition(Int, Int)`  | Specifies custom activity transition animations for closing the activity. More details [here](https://github.com/marcauberer/simple-settings/wiki/activity-configuration#custom-activity-transitions). |

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
-   French

New translations are highly appreciated. If you want to translate the lib, please open a pr.

## Contribute to the project
If you want to contribute to this project, please feel free to send us a pull request.

## Used third party libraries

- [AboutLibraries by Mike Penz](https://github.com/mikepenz/AboutLibraries)
- [FastAdapter by Mike Penz](https://github.com/mikepenz/FastAdapter)
- [LivePreferences by İbrahim Süren](https://github.com/ibrahimsn98/live-preferences)
- [ColorPickerPreference by Jaewoong Eum](https://github.com/skydoves/ColorPickerPreference)

## Credits
Thanks to all contributors and translators!

© Marc Auberer 2020-2022
