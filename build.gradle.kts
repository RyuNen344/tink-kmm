import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application").version("7.4.2").apply(false)
    id("com.android.library").version("7.4.2").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin").version("1.3.0")
    id("io.gitlab.arturbosch.detekt").version("1.23.0-RC1")
    id("com.louiscad.complete-kotlin").version("1.1.0")
    id("jacoco")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.0-RC1")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.23.0-RC3")
}

detekt {
    parallel = true
    ignoreFailures = true
    config.setFrom(files("${rootProject.rootDir}/.detekt/config.yml"))
    source.setFrom(
        files(
            fileTree("${rootProject.rootDir}").matching {
                include("**/*.kt", "**/*.kts")
                exclude("**/build/")
                exclude("TinkStub")
            }
        )
    )
}

val localProperty = java.util.Properties().apply {
    File(rootDir, "local.properties").takeIf { it.exists() }?.run {
        load(inputStream())
    }
}

allprojects {
    group = "io.github.ryunen344.tink"
    version = File("${rootProject.rootDir}/version.txt").readText().trim()
    tasks.withType(KotlinCompile::class) {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            languageVersion = "1.8"
            apiVersion = "1.8"
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(localProperty.getProperty("sonatype.username") ?: System.getenv("SONATYPE_USERNAME"))
            password.set(localProperty.getProperty("sonatype.password") ?: System.getenv("SONATYPE_PASSWORD"))
        }
    }
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    publishing {
        publications.withType<MavenPublication> {
            pom {
                name.set("Tink KMM")
                description.set("Allows you to use Tink Primitive Encryption in your Kotlin Multiplatform Mobile project")
                url.set("https://github.com/RyuNen344/tink-kmm")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
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
        repositories {
            maven {
                name = "Local"
                setUrl("$rootDir/releases/maven")
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
}

jacoco {
    version = "0.8.9"
}

tasks.create<JacocoReport>("jacocoMergedReport") {
    group = "verification"
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }

    project.gradle.afterProject {
        if (rootProject != this && plugins.hasPlugin("jacoco")) {
            executionData.from.addAll(
                fileTree(buildDir) {
                    includes += mutableSetOf("**/*.exec", "**/*.ec")
                }
            )
            sourceDirectories.from.addAll(
                listOf(
                    "$projectDir/src/androidMain/kotlin",
                    "$projectDir/src/commonMain/kotlin",
                    "$buildDir/generated/source/kapt/debug",
                )
            )
            classDirectories.from.addAll(
                listOf(
                    "$buildDir/tmp/kotlin-classes/debug",
                    "$buildDir/intermediates/javac/debug/classes"
                )
            )
        }
    }
}
