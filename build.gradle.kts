plugins {
    kotlin("jvm") version "1.9.21"
}

group = "ru.nsu.e.shelbogashev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("io.github.microutils:kotlin-logging:3.0.5")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}