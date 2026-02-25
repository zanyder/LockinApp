# Lockin Focus Blocker

Lockin Focus Blocker is an Android app built with Kotlin and Jetpack Compose that helps you stay focused by blocking distracting apps during time-bound focus sessions.

## Features

- Select which apps to block during focus sessions.
- Start configurable focus sessions with strict or flexible modes.
- Foreground service monitors which app is currently in use and brings up a blocker screen when a blocked app is opened.

## Getting Started

1. Open this project in Android Studio (Giraffe or newer recommended).
2. Ensure you have the latest Android SDKs installed.
3. Sync Gradle and run the `app` configuration on a device or emulator (API 26+ recommended).

On first launch, you will be guided to grant Usage Access permission so the app can detect which app is in the foreground. Optional Accessibility-based detection can be added later.

