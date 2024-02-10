plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.HILT_ANDROID)
    id(Plugins.GMS_GOOGLE_SERVICE)
}

android {
    namespace = "com.nyangzzi.dayFlower"
    compileSdk = DefaultConfig.COMPLIE_SDK

    defaultConfig {
        applicationId = "com.nyangzzi.dayFlower"
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        buildConfigField(
            DefaultConfig.API_KEY_TYPE,
            DefaultConfig.SEARCH_FLOWER,
            getApiKey(DefaultConfig.SEARCH_FLOWER)
        )

        buildConfigField(
            DefaultConfig.API_KEY_TYPE,
            DefaultConfig.KAKAO_STRING,
            getApiKey(DefaultConfig.KAKAO_STRING)
        )

        buildConfigField(
            DefaultConfig.API_KEY_TYPE,
            DefaultConfig.NAVER_CLIENT_ID,
            getApiKey(DefaultConfig.NAVER_CLIENT_ID)
        )

        buildConfigField(
            DefaultConfig.API_KEY_TYPE,
            DefaultConfig.NAVER_CLIENT_SECRET,
            getApiKey(DefaultConfig.NAVER_CLIENT_SECRET)
        )

        manifestPlaceholders[DefaultConfig.KAKAO] = getApiKey(DefaultConfig.KAKAO)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            /*proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )*/
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    kotlin {
        jvmToolchain(11)
    }
    buildFeatures {
        compose = true
        dataBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleRuntimeCompose)
    implementation(platform(Dependencies.composeBom))

    //compose
    implementation(Dependencies.composeActivity)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterial3)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.composeFoundation)

    //hilt
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidComplier)
    implementation(Dependencies.hiltNavigationCompose)

    //retrofit
    implementation(Dependencies.gson)
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.convertGson)
    implementation(Dependencies.retrofitScalars)
    implementation(Dependencies.retrofitSimpleXml)

    //tikxml
    implementation(Dependencies.tikxmlAnnotation)
    implementation(Dependencies.tikxmlCore)
    implementation(Dependencies.tikxmlRetrofitConverter)
    kapt(Dependencies.tikxmlProcessor)

    //coil compose
    implementation(Dependencies.coilCompose)

    //viewpager
    implementation(Dependencies.accompanistPager)
    implementation(Dependencies.accompanistPagerIndicators)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testExtJunit)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.uiTestJunit)

    debugImplementation(Dependencies.uiTooling)
    debugImplementation(Dependencies.uiTestManifest)

    //login
    implementation(Dependencies.kakaoSdk) // 카카오 로그인
    implementation(Dependencies.naverOauth) //네이버 로그인

    //firebase
    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.firebaseaAthKtx)
    implementation(Dependencies.firebaseAuth)
    implementation(Dependencies.firebaseDatabase)
    implementation(Dependencies.firestoreKtx)

    //datastore
    implementation(Dependencies.datastore)
}

kapt {
    correctErrorTypes = true
}


fun getApiKey(propertyKey: String): String {
    return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty(propertyKey)
}
