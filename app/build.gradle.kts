import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Added:
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)
    alias(libs.plugins.kotlinxSerialization) // To process @Serializable annotations
}

android {
    namespace = "job.challenge.movieapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "job.challenge.movieapp"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        buildConfig = true // Adds access BuildConfig obj
    }
}

dependencies {
    // Default:
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)
    // Dependencies not used since this challenge doesn't require testing, this is mainly to speed up builds
    // androidTestImplementation(libs.androidx.junit)
    // androidTestImplementation(libs.androidx.espresso.core)
    // androidTestImplementation(platform(libs.androidx.compose.bom))
    // androidTestImplementation(libs.androidx.ui.test.junit4)

    // Added:
    // Hilt
    implementation(libs.google.hilt.android)
    ksp(libs.google.hilt.android.compiler)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Icons
    implementation(libs.androidx.material.icons.extended.android)

    // Datastore, to store app settings (the bearer token)
    implementation(libs.androidx.datastore.preferences)

    // Ktor (HTTP client)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio) // Coroutine-based I/O Engine for processing network requests https://ktor.io/docs/client-engines.html#jvm-android-native
    implementation(libs.ktor.client.content.negotiation) // Used for Serialization of JSONs https://ktor.io/docs/client-serialization.html
    implementation(libs.ktor.serialization.kotlinx.json) // Used for Serialization. The annotations are processed by the kotlinxSerialization Gradle Plugin
}

/**
 * Enables context receivers
 * https://kotlinlang.org/docs/whatsnew1620.html#prototype-of-context-receivers-for-kotlin-jvm
 * https://kotlinlang.org/docs/whatsnew2020.html#phased-replacement-of-context-receivers-with-context-parameters
 */
tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs = listOfNotNull("-Xcontext-receivers")
    }
}