import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

kotlin {
    android {
        publishLibraryVariants("release")
    }

    val frameworkName = "TinKMM"
    val xcf = XCFramework(frameworkName)

    listOf(
        iosX64(),
        iosSimulatorArm64(),
        iosArm64(),
    ).forEach {
        it.compilations.named("main") {
            cinterops.create("Tink") {
                compilerOpts(tinkOption(it.konanTarget))
            }
        }
        it.binaries {
            framework {
                baseName = frameworkName
                embedBitcode(BitcodeEmbeddingMode.DISABLE)
                binaryOption("bundleId", "io.github.ryunen344.tink")
                binaryOption("bundleVersion", version.toString())
                linkerOpts(tinkOption(it.konanTarget))
                xcf.add(this)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun Project.xcfPath(target: KonanTarget): String {
    return "$rootDir/Framework/Tink.xcframework/" +
        if (target is KonanTarget.IOS_ARM64) "ios-arm64" else "ios-arm64_x86_64-simulator"
}

fun Project.tinkOption(target: KonanTarget): List<String> {
    return listOf(
        "-framework",
        "Tink",
        "-F${xcfPath(target)}"
    )
}

publishing {
    repositories {
        maven {
            name = "Local"
            setUrl("$rootDir/releases/maven")
        }
    }
}
