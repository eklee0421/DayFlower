// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version "8.1.0" apply false
    id(Plugins.KOTLIN_ANDROID) version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}