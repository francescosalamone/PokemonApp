plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.francescosalamone.pokemonapp"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.4"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")

    /**
     * JETPACK COMPOSE DEPENDENCIES
     */
    val composeVersion = "1.0.4"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01")


    /**
     * KOIN DEPENDENCIES
     */
    val koinVersion = "3.1.3"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")

    /**
     * UNIFLOW
     */
    val uniflowVersion = "1.0.10"
    implementation("org.uniflow-kt:uniflow-core:$uniflowVersion")
    implementation("org.uniflow-kt:uniflow-android:$uniflowVersion")
    implementation(project(":data"))

    /**
     * LOGGER DEPENDENCIES
     */
    implementation("com.jakewharton.timber:timber:4.7.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}