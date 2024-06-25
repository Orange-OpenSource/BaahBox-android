# French and english documentation about the project and the box

Note this project is deprecated and has been replaced by the [Flutter version](https://github.com/Orange-OpenSource/BaahBox-Flutter-android).

Français | English
------------- | -------------
[![BaahBoxPix](https://github.com/Orange-OpenSource/BaahBox-Arduino/blob/dev/documentation/img/fr.jpg)](https://github.com/Orange-OpenSource/BaahBox-Arduino/blob/dev/documentation/fr/README.md) | [![BaahBoxPix](https://github.com/Orange-OpenSource/BaahBox-Arduino/blob/dev/documentation/img/en.jpg)](https://github.com/Orange-OpenSource/BaahBox-Arduino/blob/dev/documentation/en/README.md)| Content 

![BaahBoxPix](https://github.com/Orange-OpenSource/BaahBox-Arduino/blob/dev/documentation/img/photoBaaBox.jpg)

# About the Android project

## List of files

* LICENSE.txt: The whole GPL 3 license
* README.md: This file
* THIRD_PARTY.txt: Contains the list of librair-ies in use
* HOWTO.md: Contains some details about games implementations or configuration
* CONTRIBUTE.md: Contains advices and workflows to contribute to the project in a clean way
* AUTHORD.md: Contains the list of authors who worked on thiss project (Android part)
* CHANGELOG.md: Changelog automatically updated

## List of directories

* tools: Here you can find a bunch of Shell script which automate tasks (change version code, generate documentation, run tests, check assets...)
* project: The Android Studio project
* licenses: The licenses files for the third party tools in use
* libs: Other libs used locally
* doc: Will contain more details

## About the scripts

### prepare-release.sh

This script prepares the release by triggering several tasks:
* checks the app configuration (properties files)
* checks the credits in the assets' metadata
* runs the unit tests and screams if some of them didn't pass
* runs functional/automated tests and screems also if some of them didn't pass
* updates the version code
* updates the documentation (HTML format)
* updates the changelog

In fact it just calls the following scripts.

### check-appconfiguration.sh

Triggers the Gradle plugin stored in _libs/_ which will check properties file using rules.
You can have a look on it's repo: https://github.com/Orange-OpenSource/gradle-properties-checker

### check-imagecredits.sh

Triggers the Gradle plugin stored in _libs/_ which will w-check in images' metadata if the legal mentions are defined.
You can have a look on it's repo: https://github.com/Orange-OpenSource/copyright-checker

### run-instrumentedtests.sh

Runs the instrumented/functional tests and check the results.
Says if the results match a score confidence or not.

### run-unittests.sh

Runs the unit tests and check the results.
Says if the results match a score confidence or not.

### update-changelog.sh

Triggers the project's Gradle tasks which will check the Git history to update the changelog.

### update-documentation.sh

Updates the documentation ussing Dokka and produce HTML content.

### update-versioncode.sh

Update the version code which mus tbe at a specific line defined in the script.

## Tools in use

So as to generated HTML doc, Dokka tool and KDoc format are used:
* https://kotlinlang.org/docs/reference/kotlin-doc.html
* https://github.com/Kotlin/dokka

## Properties management

So as to export configuration from source code, properties files are stored in _assets_.
Such files allow to define configuration for the app or the games.
One file defines the configuration to apply ("app_configuration.properties") and the other defines
the rules this file must follow ("app_configuration.rules.properties").
A _Gradle_ task named "propertieschecker" will process these files to verify if the configuration is well defined.

## Copyright management

Because the project embeds assets designed by Orange designers, the _Gradle_ plugin with the task "copyrightchecker" will look in metadata for legal mentions.

## Changelog management

The changelog can be updated thanks to a _Gradle_ plugin with a task "changelog". It will parse the _Git_ history so as to update the file.
However tyou should read that and correct it if needed ; the plugin is not yet perfect.

## Icons and ribbons

A task "easylauncher" will update the app icons with ribbons. 3 ribbons have been defined reagrding the mode to use.

## Roadmap

Here are some steps to reach:
* fix display bugs on large screens
* implement spaceship game
* implement toad game
* deal with joystick
* improve move of avatars
* improve sensor frames processing
* deal with iOS app and box evolutions (more up to date)

Please refer to issues: https://github.com/Orange-OpenSource/BaahBox-Android/issues
