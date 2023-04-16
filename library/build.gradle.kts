import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosSimulatorArm64(),
        iosArm64(),
    ).forEach {
        it.compilations.named("main") {
            cinterops.create("Tink") {
                includeDirs(xcframeWorkPath(it.konanTarget) + "/Tink.framework/Headers")
            }
        }
        it.binaries {
            framework {
                baseName = "KMMTink"
                binaryOption("bundleId", "io.github.ryunen344.tink")
                binaryOption("bundleVersion", version.toString())
                linkerOpts("-framework", "Tink")
                linkerOpts("-F${xcframeWorkPath(it.konanTarget)}")
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.crypto.tink:tink-android:1.7.0")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosSimulatorArm64Main by getting
        val iosArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosSimulatorArm64Test by getting
        val iosArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "io.github.ryunen344.tink"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}

fun Project.xcframeWorkPath(target: KonanTarget): String {
    return "$rootDir/Tink/Tink.xcframework/" +
           if (target is KonanTarget.IOS_ARM64) "ios-arm64" else "ios-arm64_x86_64-simulator"
}
