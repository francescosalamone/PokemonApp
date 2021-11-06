plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
    }
}

dependencies {

    /**
     * COROUTINES DEPENDENCIES
     */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    /**
     * KOIN DEPENDENCIES
     */
    val koinVersion = "3.1.3"
    implementation("io.insert-koin:koin-core:$koinVersion")


    /**
     * LOGGER DEPENDENCIES
     */
    implementation("com.jakewharton.timber:timber:4.7.1")

    /**
     * MODULE DEPENDENCIES
     */
    implementation(project(":data"))
    implementation(project(":model"))
}
