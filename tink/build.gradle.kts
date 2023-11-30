import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("jacoco")
}

kotlin {
    androidTarget {
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
            maybeCreate("iosX64Main").dependsOn(this)
            maybeCreate("iosSimulatorArm64Main").dependsOn(this)
            maybeCreate("iosArm64Main").dependsOn(this)
        }
        create("iosTest") {
            dependsOn(getByName("commonTest"))
            maybeCreate("iosX64Test").dependsOn(this)
            maybeCreate("iosSimulatorArm64Test").dependsOn(this)
            maybeCreate("iosArm64Test").dependsOn(this)
        }
    }

    compilerOptions {
        freeCompilerArgs.set(
            freeCompilerArgs.get() + listOf(
                // for Kotlin/Native interop
                "-opt-in=kotlinx.cinterop.BetaInteropApi",
                "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                "-Xexpect-actual-classes",
            )
        )
    }
}

android {
    namespace = "io.github.ryunen344.tink"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.all { test ->
            test.testLogging.showStandardStreams = true
            jacoco.jacocoVersion = "0.8.9"
            test.extensions.configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
                excludes = listOf("jdk.internal.*")
            }
        }
    }
    buildTypes.getByName("debug") {
        enableUnitTestCoverage = true
        enableAndroidTestCoverage = true
    }
    lint {
        abortOnError = false
    }
    with(sourceSets["main"]) {
        java.srcDirs("src/androidMain/kotlin", "src/commonMain/kotlin")
        res.srcDirs("src/androidMain/res")
    }
    with(sourceSets["test"]) {
        java.srcDirs("src/androidUnitTest/kotlin", "src/commonTest/kotlin")
        res.srcDirs("src/androidUnitTest/res")
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
