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
    implementation("androidx.annotation:annotation:1.2.0")

    /**
     * KOIN DEPENDENCIES
     */
    val koinVersion = "3.1.3"
    implementation("io.insert-koin:koin-core:$koinVersion")
}