import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("org.jetbrains.compose") version "1.6.0-alpha01"
}

group = "ru.nsu.e.shelbogashev.studio.paint"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation(project(":studio-core"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "paint"
            packageVersion = "1.0.0"
        }
    }
}