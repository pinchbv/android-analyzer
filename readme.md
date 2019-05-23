# Android Analyzer Gradle Plugin

Android Analyzer is a Gradle plugin for analyzing Android projects, integrating Sonarqube and Detekt for static code analysis 
and Jacoco for Kotlin and Java code coverage reports. The plugin provides a very easy to use interface 
and abstracts away the complexity of setting up the two systems manually.

An article explaining our ambitions and results related to the plugin, as well as an integration guide, can be found here: https://medium.com/@robertas.konarskis/android-analyzer-df0e4d80dc74

## Features

- Automatically creates and configures a Sonarqube project
- Configures relevant properties of the Sonarqube plugin
- Configures relevant properties of the Jacoco plugin
- Comes with a prepare list of default path exclusions
- Provides a simple interface for integration in most projects
- Provides one Gradle task that does all the necessary work
- Generates a default configuration file for Detekt which can be customized as required


## Requirements

- Sonarqube server


## Limitations

- Gradle 4.10.1+
- No support for Instrumented test coverage
- In multi-module projects, each module is treated as a separate Sonarqube project 


## Android Integration

*NOTE*: an example project using the plugin can be found in this repository.


##### Include Maven repo and a Gradle classpath dependencies

Project-level `build.gradle`:
```
allprojects {
    repositories {
        ..
        jcenter()
        maven { url "https://plugins.gradle.org/m2/" }
        maven { url "https://raw.github.com/pinchbv/android-analyzer/master/repo" }
    }
    dependencies {
        ..
        classpath "com.justpinch:androidanalyzer:1.2.0"
    }
}
```

Application-level `build.gradle`:
```
apply plugin: 'com.justpinch.androidanalyzer'
..
androidAnalyzer {
    applicationId = 'com.konarskirob.androidsonar'
    projectName = 'Android Analyzer Example App'
    projectVersion = '0.9.0'
    detekt = true
    unitTestCoverage = true
    packageName = 'com.konarskirob.androidsonar'
    buildVariant = 'debug'
}
```

-----------------------------------------------------------

If your project does not contain any unit tests, then you should not set `unitTestCoverage`, `packageName` 
and `buildVariant` properties.

-----------------------------------------------------------

If you want advanced Kotlin code analysis using Detekt, you must set `detekt = true` as per the example above.
Please note that this will generate a configuration file (default name `detekt-config.yml`) in the root folder 
of your application.

-----------------------------------------------------------

CLI command to run the plugin (single-module project):
```
./gradlew androidAnalyzer
```

CLI command to run the plugin (multi-module project):
```
// when running the plugin in multi-module environment, make sure to analyze each module separately
./gradlew app:androidAnalyzer
./gradlew core:androidAnalyzer
./gradlew feature:androidAnalyzer
```

CLI command to get a list of default exclusions:
```
./gradlew androidAnalyzerDefaultExclusions
```

CLI command to print a default detekt configuration file:
```
./gradlew androidAnalyzerDefaultDetektConfig
```


## Configuration

Android Analyzer plugin provides a Gradle extension called `androidAnalyzer` for configuring the behavior of the plugin.  
Below is a list of available properties with corresponding descriptions.  

##### Required

| property              | type          | default value           | description |
| :-------------------- | :------------ | :---------------------- | :---------- |
| applicationId         | String        | null                    | Unique Android application ID used in Sonarqube project key generation. E.g. and application ID of `my.project` will result in the following Sonarqube project key: `my.project-android`. |
| projectName           | String        | null                    | Sonarqube project display name. Will not be overwritten if project is already present. |

##### Optional

| property              | type          | default value           | description |
| :-------------------- | :------------ | :---------------------- | :---------- |
| projectVersion        | String        | undefined               | Sonarqube display project version. Typically same as app's `versionName`. |
| projectVisibility     | String        | private                 | Sonarqube project visibility. Can be `public` or `private`. Will not be overwritten if project is already present. |
| detekt                | Boolean       | false                   | Toggle Kotlin code analysis with Detekt. This will generate a configuration file and place it in the application directory. To see how a default configuration file looks like, run `./gradlew androidAnalyzerDefaultDetektConfig`. |
| detektConfigFileName  | String        | detekt-config.yml       | Detekt configuration file name. |
| detektBaseline        | String        | null                    | Path to detekt baseline file. For more information, please visit documentation of detekt. |
| unitTestCoverage      | Boolean       | false                   | Toggle unit test coverage reports by Jacoco. If enabled, `packageName` and `buildVariant` must be specified too. |
| packageName           | String        | null                    | Package name of your Android project. Must match your folder structure, e.g. `nl.pinch.appname`. |
| buildVariant          | String        | null                    | Variant for running unit tests and generating code coverage reports. |
| serverUrl             | String        | http://localhost:9000   | URL of Sonarqube server. Can also be specified using an environment variable (see Server Integration section). |
| useDefaultExclusions  | Boolean       | true                    | Toggle default exclusions from analysis and coverage reports. To get a list of default exclusions, call Gradle task `androidAnalyzerDefaultExclusions`. |
| customExclusions      | Array(String) | emptyList(String)       | Provide a list of custom exclusions from analysis and coverage reports. |
| sonarqubeUsername     | String        | admin                   | Sonarqube username. Prefer environment variables over this method for better security. |
| sonarqubePassword     | String        | admin                   | Sonarqube password. Prefer environment variables over this method for better security. |
| sonarqubeToken        | String        | null                    | Sonarqube token. Prefer environment variables over this method for better security. |


## Robolectric

For Robolectric support, add the following snippet to your app-level `build.gradle` file:
```
android {
    testOptions {
        unitTests.all {
            jacoco {
                includeNoLocationClasses = true
            }
        }
    }
}
```

And the following line to your `gradle.properties` file:
```
android.enableUnitTestBinaryResources=true
```

*NOTE*: this might not be the only configuration needed for Robolectric and would depend on its version as well as the apps sdk level, and whether you are using androidx tests.
For more information on the subject, check the example project or follow Robolectric setup guides. 


## Sonarqube Server / CI Integration

#### Server URL
In addition to supplying Sonarqube URL via the gradle extension, it can be passed as an anvironment variable:
```
ANDROID_ANALYZER_SONARQUBE_URL
```
If both the environment variable and the `serverUrl` gradle parameter are specified, the environment variable will be used.

#### Authentication

There are two ways to provide authentication to the Sonarqube server.  
The preferred way is using environment variables. You can either supply your Sonarqube credentials: 
```
ANDROID_ANALYZER_SONARQUBE_USERNAME
ANDROID_ANALYZER_SONARQUBE_PASSWORD
```
The plugin would then use these credentials to generate an authentication token.

EXPERIMENTAL: Alternatively, if you don't want to expose your Sonarqube credentials, you can generate a token in Sonarqube wen interface and supply it to the plugin directly:
```
ANDROID_ANALYZER_SONARQUBE_TOKEN
```
If both token and username/password are provided, token will be used and username/password will be ignored.
This is the preferred way of setting up authentication in case your CI config file is in version control. 

The less secure way is using the Gradle extension. See `sonarqubeUsername`, `sonarqubePassword` and `sonarqubeToken` properties the Android integration part.


## Changelog

#### Version 1.2.0 - May 24, 2019
Sonarqube auth token stable.



-----------------------------------------------------------

#### Version 1.1.2 - May 23, 2019
Added experimental support for passing Sonarqube auth token directly without user credentials.

Added support for passing Sonarqube server URL using an environment variable.

-----------------------------------------------------------

#### Version 1.1.1 - May 12, 2019
Renamed confusing `buildFlavor` parameter to a more correct `buildVariant`.

-----------------------------------------------------------
#### Version 1.1.0 - April 6, 2019
Detekt integration for advanced Kotlin code analysis.

-----------------------------------------------------------
#### Version 1.0.2 - April 5, 2019 (Breaking)
Improved Sonarqube project key generation from application ID.

-----------------------------------------------------------

#### Version 1.0.1 - April 3, 2019
Added a task to get a list of default exclusions.

-----------------------------------------------------------

#### Version 1.0.0 - April 2, 2019
Plugin is born.

-----------------------------------------------------------
