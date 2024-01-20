group = "ru.nsu.e.shelbogashev.studio.core"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}