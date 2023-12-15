plugins {
    kotlin("jvm")
}

dependencies {
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

    testImplementation(platform("org.junit:junit-bom:5.10.1"))

    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
