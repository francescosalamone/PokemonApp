plugins {
    id("kotlin")
    kotlin("kapt")
}

dependencies {
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    /**
     * ROOM DEPENDENCIES
     */
    val roomVersion = "2.3.0"
    implementation("androidx.room:room-common:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
}