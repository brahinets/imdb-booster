plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":domain"))

    implementation("com.opencsv:opencsv:5.9")
}
