plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
    }
}

dependencies {

    /**
     * RETROFIT DEPENDENCIES
     */
    val retrofitVersion = "2.9.0"
    api("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("androidx.annotation:annotation:1.3.0")

    /**
     * ROOM DEPENDENCIES
     */
    val roomVersion = "2.3.0"
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    /**
     * KOIN DEPENDENCIES
     */
    val koinVersion = "3.1.3"
    implementation("io.insert-koin:koin-core:$koinVersion")

    /**
     * COROUTINES DEPENDENCIES
     */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    /**
     * MODULE DEPENDENCIES
     */
    implementation(project(":model"))


    /**
     * LOGGER DEPENDENCIES
     */
    implementation("com.jakewharton.timber:timber:4.7.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}