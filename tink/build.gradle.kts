import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
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
                compilerOpts(tinkCompilerOption(it.konanTarget))
            }
        }
        it.binaries {
            getTest(NativeBuildType.DEBUG).linkerOpts(tinkLinkerOption(it.konanTarget))
            framework {
                baseName = frameworkName
                embedBitcode(BitcodeEmbeddingMode.DISABLE)
                binaryOption("bundleId", "io.github.ryunen344.tink")
                binaryOption("bundleVersion", version.toString())
                binaryOption("bundleShortVersionString", version.toString())
                linkerOpts(tinkLinkerOption(it.konanTarget))
                xcf.add(this)
            }
        }
    }

    sourceSets {
        getByName("commonTest").dependencies {
            implementation(kotlin("test"))
        }
        getByName("androidMain").dependencies {
            implementation("com.google.crypto.tink:tink-android:1.7.0")
        }
        create("iosMain") {
            dependsOn(getByName("commonMain"))
            getByName("iosX64Main").dependsOn(this)
            getByName("iosSimulatorArm64Main").dependsOn(this)
            getByName("iosArm64Main").dependsOn(this)
        }
        create("iosTest") {
            dependsOn(getByName("commonTest"))
            getByName("iosX64Test").dependsOn(this)
            getByName("iosSimulatorArm64Test").dependsOn(this)
            getByName("iosArm64Test").dependsOn(this)
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

fun Project.tinkCompilerOption(target: KonanTarget): List<String> {
    return listOf(
        "-framework",
        "Tink",
        "-F${xcfPath(target)}",
    )
}

fun Project.tinkLinkerOption(target: KonanTarget): List<String> {
    return tinkCompilerOption(target) + listOf(
        "-rpath",
        xcfPath(target),
        "-ObjC",
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
