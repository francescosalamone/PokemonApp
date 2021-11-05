plugins {
    id("kotlin")
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

}