# AGENTS.md

## Cursor Cloud specific instructions

### Project overview

Native Android app (Kotlin + Jetpack Compose) — "Lockin Focus Blocker". No backend services, no Docker. Uses Room (SQLite) for local storage.

### Prerequisites

- **Android SDK** must be installed at `/opt/android-sdk` with `ANDROID_HOME` set (persisted in `~/.bashrc`).
- Required SDK packages: `platforms;android-35`, `build-tools;35.0.0`, `platform-tools`.
- **JDK 17+** (JDK 21 works fine).
- **Gradle 8.7+** for wrapper generation (installed at `/opt/gradle/gradle-8.7`).

### Build, lint, and test commands

| Task | Command |
|---|---|
| Build debug APK | `./gradlew assembleDebug` |
| Run lint | `./gradlew lint` |
| Run unit tests | `./gradlew test` |
| Build + install on connected device | `./gradlew installDebug` |

### Emulator caveats

- This cloud VM **does not have KVM**. The Android emulator runs in software mode (`-no-accel -gpu swiftshader_indirect`), which is extremely slow.
- Emulator boot takes 5-10+ minutes without KVM. Screenshot capture may produce black images after extended operation due to swiftshader instability.
- To create an AVD: `echo "no" | avdmanager create avd -n test_device -k "system-images;android-35;google_apis;x86_64" --device "pixel_6" --force`
- To launch the emulator: `emulator -avd test_device -no-window -no-audio -no-boot-anim -gpu swiftshader_indirect -no-accel &`
- Install and launch via: `adb install app/build/outputs/apk/debug/app-debug.apk && adb shell am start -n com.lockinapp.focusblocker/.MainActivity`

### Build fixes applied to the repo

The following fixes were required to make the project build (the original repo had config issues):

1. **Gradle wrapper** was missing — generated `gradlew`, `gradlew.bat`, `gradle/wrapper/`.
2. **`gradle.properties`** was missing — added `android.useAndroidX=true` and `android.suppressUnsupportedCompileSdk=35`.
3. **`allprojects` block** in root `build.gradle.kts` conflicted with `FAIL_ON_PROJECT_REPOS` in `settings.gradle.kts` — removed it.
4. **Compose Compiler plugin** (`org.jetbrains.kotlin.plugin.compose`) required for Kotlin 2.0 — added to `app/build.gradle.kts`.
5. **Material Components** (`com.google.android.material:material`) needed for XML `Theme.Material3.DayNight.NoActionBar` — added dependency.
6. **Launcher icons** (`mipmap` resources) were missing — created placeholders.
7. **Import error** in `OnboardingScreen.kt` (`FillMaxSize` doesn't exist) — removed.
8. **Experimental API opt-in** for `TopAppBar` in `HomeScreen.kt` — added `@OptIn(ExperimentalMaterial3Api::class)`.
9. **Manifest permissions** — added `FOREGROUND_SERVICE_DATA_SYNC`, suppressed lint `ProtectedPermissions` for `PACKAGE_USAGE_STATS`.

### No existing tests

The project declares test dependencies but has no `src/test/` or `src/androidTest/` directories. `./gradlew test` succeeds with `NO-SOURCE`.
