plugins {
    id("kotlin")
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
     * MODULE DEPENDENCIES
     */
    implementation(project(":data"))
    implementation(project(":model"))
}
