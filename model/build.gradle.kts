plugins {
    id("kotlin")
}

dependencies {
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
}