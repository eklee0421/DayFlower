object Dependencies {

    //androidx compose
    const val composeActivity = "androidx.activity:activity-compose:${Versions.COMPOSE_AVTIVITY}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"

    //hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.HILT_ANDROID}"
    const val hiltAndroidComplier = "com.google.dagger:hilt-android-compiler:${Versions.HILT_ANDROID_COMPLER}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.HILT_COMPOSE}"

    //retrofit
    const val gson = "com.google.code.gson:gson:${Versions.GSON}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val convertGson = "com.squareup.retrofit2:converter-gson:${Versions.CONVERTER_GSON}"
    const val retrofitScalars = "com.squareup.retrofit2:converter-scalars:${Versions.RETROFIT_SCALARS}"
    
    //okhttp
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP3}"
    const val okhttp3Logging = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP3}"
    const val okhttp3UrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:${Versions.OKHTTP3}"
}