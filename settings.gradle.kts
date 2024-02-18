plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "engineering-computer-graphics"
include("studio-core")
include("paint")
include("paint2")
include("raytracing-pure")
include("wireframe-test")
