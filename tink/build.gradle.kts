import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.util.Properties

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("signing")
    id("jacoco")
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

val localProperty = Properties().apply {
    File(rootDir, "local.properties").takeIf { it.exists() }?.run {
        load(inputStream())
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            pom {
                name.set("Tink KMM")
                description.set("Allows you to use Tink Primitive Encryption in your Kotlin Multiplatform Mobile project")
                url.set("https://github.com/RyuNen344/tink-kmm")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("RyuNen344")
                        name.set("Bunjiro Miyoshi")
                        email.set("s1100633@outlook.com")
                    }
                }
                scm {
                    connection.set("scm:git://github.com/RyuNen344/tink-kmm.git")
                    developerConnection.set("scm:git://github.com/RyuNen344/tink-kmm.git")
                    url.set("https://github.com/RyuNen344/tink-kmm")
                }
            }
        }
    }
    repositories {
        maven {
            name = "Local"
            setUrl("$rootDir/releases/maven")
        }
        maven {
            name = "MavenCentral"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = localProperty.getProperty("sonatype.username") ?: System.getenv("SONATYPE_USERNAME")
                password = localProperty.getProperty("sonatype.password") ?: System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

signing {
    val keyId = localProperty.getProperty("pgp.key_id") ?: System.getenv("PGP_KEY_ID")
    val secretKey = localProperty.getProperty("pgp.signing_key") ?: System.getenv("PGP_SIGNING_KEY")
    val password = localProperty.getProperty("pgp.signing_password") ?: System.getenv("PGP_SIGNING_PASSWORD")
    useInMemoryPgpKeys(keyId, secretKey, password)
    sign(publishing.publications)
}
