# Simple Settings Activity

## Usage
The library accepts two different ways, how you provide the settings screen information.

### Provide items programmatically


### Provide items with xml file
You also can specify your preference screen (as usual as xml file)[https://developer.android.com/guide/topics/ui/settings#create_a_hierarchy]:
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

## Contribute
If you want to contribute to this project, please feel free to send me a pull request.

## Credits


© Marc Auberer 2020