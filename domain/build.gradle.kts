plugins {
    kotlin("jvm")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))

    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
