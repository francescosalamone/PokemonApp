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

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
}
