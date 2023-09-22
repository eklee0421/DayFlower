// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.AGP apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.AGP apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
}

buildscript {
    dependencies {
        classpath(Dependencies.hiltPlugin)
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}