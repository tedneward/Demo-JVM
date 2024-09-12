plugins {
    kotlin("multiplatform") version "1.6.21"
}

repositories {
    mavenCentral()
}

kotlin {
  linuxX64("native") { // on Linux
  // macosX64("native") { // on x86_64 macOS
  // macosArm64("native") { // on Apple Silicon macOS
  // mingwX64("native") { // on Windows
    binaries {
      sharedLib {
        baseName = "native" // on Linux and macOS
        // baseName = "libnative" // on Windows
      }
    }
  }
}

tasks.wrapper {
  gradleVersion = "6.7.1"
  distributionType = Wrapper.DistributionType.ALL
}
