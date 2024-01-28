plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.HILT_ANDROID)
    id("com.google.gms.google-services")
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
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    //compose
    implementation(Dependencies.composeActivity)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterial3)
    implementation(Dependencies.navigationCompose)
    implementation("androidx.compose.foundation:foundation:1.3.0")

    //hilt
    implementation(Dependencies.hiltAndroid)
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.10.1")
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

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //login
    implementation("com.kakao.sdk:v2-user:2.19.0") // 카카오 로그인
    implementation("com.navercorp.nid:oauth:5.9.0") //네이버 로그인

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
}

kapt {
    correctErrorTypes = true
}


fun getApiKey(propertyKey: String): String {
    return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty(propertyKey)
}
