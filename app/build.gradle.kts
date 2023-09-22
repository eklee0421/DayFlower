plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.HILT_ANDROID)
    id(Plugins.KAPT)
}

android {
    namespace = "com.nyang.dayFlower"
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.nyang.dayFlower"
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNTER
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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

    //androidx
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleRuntimeCompose)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.composeUI)
    implementation(Dependencies.composeUIToolingPreview)
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    implementation(Dependencies.material)
    implementation(Dependencies.composeNavigation)
    implementation("androidx.appcompat:appcompat:1.6.1")

    testImplementation(Testing.composeUiTooling)
    testImplementation(Testing.composeUiTestJunit)
    testImplementation(Testing.composeUiTestManifest)
    testImplementation(Testing.junit)
    testImplementation(Testing.testExtJunit)
    testImplementation(Testing.espressoCore)

    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltNavigationCompose)
    implementation("androidx.compose.animation:animation:1.3.3")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")

    implementation(Dependencies.accompanist_drawablepainter)
    implementation(Dependencies.splashScreen)
    implementation("com.google.accompanist:accompanist-drawablepainter:0.28.0")
    implementation("androidx.compose.material:material-icons-extended:1.2.0")

    implementation(Dependencies.gson)
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.convertGson)
    implementation("com.squareup.retrofit2:converter-scalars:2.3.0")
}