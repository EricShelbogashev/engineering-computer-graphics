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
        implementation("com.formdev:flatlaf:3.3")
        implementation("ch.qos.logback:logback-classic:1.4.14")
        implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}