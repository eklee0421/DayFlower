object Dependencies {

    const val coreKtx = "androidx.core:core-ktx:${Versions.CORE_KTX}"

    //lifecycle
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME}"
    const val lifecycleRuntimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.LIFECYCLE_RUNTIME}"

    //androidx compose
    const val composeActivity = "androidx.activity:activity-compose:${Versions.COMPOSE_AVTIVITY}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.NAVIGATION_COMPOSE}"

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
    const val retrofitSimpleXml = "com.squareup.retrofit2:converter-simplexml:${Versions.RETROFIT_SIMPLE_XML}"
    
    //tikxml
    const val tikxmlAnnotation = "com.tickaroo.tikxml:annotation:${Versions.TIKXML}"
    const val tikxmlCore = "com.tickaroo.tikxml:core:${Versions.TIKXML}"
    const val tikxmlRetrofitConverter = "com.tickaroo.tikxml:retrofit-converter:${Versions.TIKXML}"
    const val tikxmlProcessor = "com.tickaroo.tikxml:processor:${Versions.TIKXML}"

    //coil compose
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.COIL_COMPSE}"

    //view pager
    const val accompanistPager = "com.google.accompanist:accompanist-pager:${Versions.ACCOMPANIST_PAGER}"
    const val accompanistPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:${Versions.ACCOMPANIST_PAGER}"
}