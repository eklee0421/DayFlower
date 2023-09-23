object Dependencies {
    // Android
    const val coreKtx = "androidx.core:core-ktx:${Versions.KOTLIN}"
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME_KTX}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.COMPOSE_UI}"
    const val composeUIToolingPreview =
        "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_UI}"
    const val composeRuntimeLiveData =
        "androidx.compose.runtime:runtime-livedata:${Versions.COMPOSE_UI}"
    const val composeNavigation = "androidx.navigation:navigation-compose:2.5.3"
    const val material = "androidx.compose.material3:material3:${Versions.MATERIAL}"
    const val androidMaterial = "com.google.android.material:material:${Versions.ANDROID_MATERIAL}"
    const val lifecycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.LIFECYCLE_RUNTIME_COMPOSE}"
    const val datastore = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"
    const val accompanist_drawablepainter =
        "com.google.accompanist:accompanist-drawablepainter:${Versions.ACCOMPANIST_DRAWABLEPAINTER}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.SPLASH_SCREEN}"
    const val workRuntimeKtx = "androidx.work:work-runtime-ktx:${Versions.WORK_RUNTIME_KTX}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"

    // third party library
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.HILT_ANDROID}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.HILT_ANDROID}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_ANDROID}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.HILT_COMPOSE}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}"

    //retrofit
    const val gson = "com.google.code.gson:gson:${Versions.GSON}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val convertGson = "com.squareup.retrofit2:converter-gson:${Versions.CONVERTER_GSON}"

    // log4J
    const val log4j = "log4j:log4j:1.2.17"
    const val log4jAndroid = "de.mindpipe.android:android-logging-log4j:1.0.3"

    //Room
    const val roomKtx = "androidx.room:room-ktx:${Versions.ROOM}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.ROOM}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.ROOM}"

}

