# Simple Settings Activity
![Android CI](https://github.com/marcauberer/simple-settings/workflows/Android%20CI/badge.svg)
[![API](https://img.shields.io/badge/API-21%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

Simple Settings is a library, which provides a simple to use, lightweight solution to create a settings screen without any boilerplate code. This behaviour saves cost, time and effort.

## Screenshots

## Try it


## Usage
The first step for using this library is, to add it to the dependency section in your project:
```gradle
implementation 'com.chillibits:simle-settings:1.0.0'
```

The library accepts two different ways, for providing the settings screen information.

### Provide items programmatically
You can create the settings items, by using the `show()` method with the callback like this:
```kotlin
val settings = SimpleSettings(this, config).show {
    Section {
        title = "Test section"
        for (i in 0..4) {
            SimpleSwitchPref {
                title = "Test 1.$i"
                summary = "This is a Test 1.$i"
                defaultValue = if(i % 2 == 0) SimpleSwitchPreference.ON else SimpleSwitchPreference.OFF
            }
        }
        if(true) {
            SimpleTextPref {
                title = "Test 2"
                summary = "This is a Test 2"
            }
        }
    }
    Section {
        SimpleInputPref {
            title = "Test 3"
            summary = "This is a Test 3"
        }
    }
}
```
This is especially useful, when you need to generate your preferences at runtime. You can use loops and conditions as you can see above.

You can optionally pass an object of `SimpleSettingsConfig` to the constructor of your `SimpleSettings` instance, to customize the appearance of the settings activity. The different customization options are listed [#customization](below).

### Provide items with xml file
You also can specify your preference screen [https://developer.android.com/guide/topics/ui/settings#create_a_hierarchy](as an usual xml file):
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
    </PreferenceCategory>
</PreferenceScreen>
```

to 

## Customization
The library offers a few customization options. For applying those options, you have to pass an object of `SimpleSettingsConfig` to the constructor of your `SimpleSettings` instance.

## Contribute to the project
If you want to contribute to this project, please feel free to send me a pull request.

## Credits


© Marc Auberer 2020