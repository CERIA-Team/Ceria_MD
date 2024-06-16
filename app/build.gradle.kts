import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    id("androidx.navigation.safeargs")
}
val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}
android {
    namespace = "com.ceria.capstone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ceria.capstone"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["redirectSchemeName"] = "spotify-sdk"
        manifestPlaceholders["redirectHostName"] = "auth"
        buildConfigField(
            "String",
            "SPOTIFY_CLIENT_ID",
            "\"${localProperties.getProperty("SPOTIFY_CLIENT_ID")}\""
        )
        buildConfigField(
            "String",
            "SPOTIFY_CLIENT_SECRET",
            "\"${localProperties.getProperty("SPOTIFY_CLIENT_SECRET")}\""
        )
        buildConfigField(
            "String",
            "CERIA_API_BASE_URL",
            "\"${localProperties.getProperty("CERIA_API_BASE_URL")}\""
        )
        buildConfigField(
            "String",
            "SPOTIFY_API_BASE_URL",
            "\"${localProperties.getProperty("SPOTIFY_API_BASE_URL")}\""
        )
        buildConfigField(
            "String",
            "SPOTIFY_AUTH_BASE_URL",
            "\"${localProperties.getProperty("SPOTIFY_AUTH_BASE_URL")}\""
        )
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    //KTX
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    //Navigation UI
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.cardview)
    //Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    //Spotify SDK
    implementation(libs.spotify.auth)
    implementation(files("../libs/spotify-app-remote-release-0.8.0.aar"))
    //Timber
    implementation(libs.timber)
    //Glide
    implementation(libs.glide)
    //Splash Screen
    implementation(libs.androidx.core.splashscreen)
    //DataStore
    implementation(libs.androidx.datastore.preferences)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}