buildscript {
    dependencies {
        classpath(Dependencies.gmsGoogleServices)
        classpath(Dependencies.gmsOssLicensesPlugin)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version "8.1.0" apply false
    id(Plugins.KOTLIN_ANDROID) version "1.8.10" apply false
    id(Plugins.HILT_ANDROID) version "2.44" apply false
    id(Plugins.GMS_GOOGLE_SERVICE) version "4.4.0" apply false
}