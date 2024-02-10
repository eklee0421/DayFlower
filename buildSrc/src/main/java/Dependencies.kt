object Dependencies {

    const val coreKtx = "androidx.core:core-ktx:${Versions.CORE_KTX}"

    //lifecycle
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME}"
    const val lifecycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.LIFECYCLE_RUNTIME}"

    //androidx compose
    const val composeActivity = "androidx.activity:activity-compose:${Versions.COMPOSE_AVTIVITY}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.NAVIGATION_COMPOSE}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
    const val composeFoundation =
        "androidx.compose.foundation:foundation:${Versions.COMPOSE_FOUNDATION}"

    //hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.HILT_ANDROID}"
    const val hiltAndroidComplier =
        "com.google.dagger:hilt-android-compiler:${Versions.HILT_ANDROID_COMPLER}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.HILT_COMPOSE}"

    //retrofit
    const val gson = "com.google.code.gson:gson:${Versions.GSON}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val convertGson = "com.squareup.retrofit2:converter-gson:${Versions.CONVERTER_GSON}"
    const val retrofitScalars =
        "com.squareup.retrofit2:converter-scalars:${Versions.RETROFIT_SCALARS}"
    const val retrofitSimpleXml =
        "com.squareup.retrofit2:converter-simplexml:${Versions.RETROFIT_SIMPLE_XML}"

    //tikxml
    const val tikxmlAnnotation = "com.tickaroo.tikxml:annotation:${Versions.TIKXML}"
    const val tikxmlCore = "com.tickaroo.tikxml:core:${Versions.TIKXML}"
    const val tikxmlRetrofitConverter = "com.tickaroo.tikxml:retrofit-converter:${Versions.TIKXML}"
    const val tikxmlProcessor = "com.tickaroo.tikxml:processor:${Versions.TIKXML}"

    //coil compose
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.COIL_COMPSE}"

    //view pager
    const val accompanistPager =
        "com.google.accompanist:accompanist-pager:${Versions.ACCOMPANIST_PAGER}"
    const val accompanistPagerIndicators =
        "com.google.accompanist:accompanist-pager-indicators:${Versions.ACCOMPANIST_PAGER}"

    const val datastore = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"

    //login
    const val kakaoSdk = "com.kakao.sdk:v2-user:${Versions.KAKAO_SDK}"
    const val naverOauth = "com.navercorp.nid:oauth:${Versions.NAVER_OAUTH}"

    //firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics"
    const val firebaseaAthKtx = "com.google.firebase:firebase-auth-ktx"
    const val firebaseAuth = "com.google.firebase:firebase-auth:${Versions.FIREBASE_AUTH}"
    const val firebaseDatabase =
        "com.google.firebase:firebase-database:${Versions.FIREBASE_DATABASE}"
    const val firestoreKtx = "com.google.firebase:firebase-firestore-ktx:${Versions.FIREBASE_KTS}"

    const val junit = "junit:junit:${Versions.JUNIT}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.TEST_EXT_JUNIT}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val uiTestJunit = "androidx.compose.ui:ui-test-junit4"
    const val uiTooling = "androidx.compose.ui:ui-tooling"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"

    const val gmsGoogleServices = "com.google.gms:google-services:${Versions.GMS_GOOGLE_SERVICE}"
}