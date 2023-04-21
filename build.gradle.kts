import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.2").apply(false)
    id("com.android.library").version("7.4.2").apply(false)
    kotlin("android").version("1.8.20").apply(false)
    kotlin("multiplatform").version("1.8.20").apply(false)
    id("io.gitlab.arturbosch.detekt").version("1.23.0-RC1")
    id("com.louiscad.complete-kotlin") version "1.1.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
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
            }
        )
    )
}

allprojects {
    group = "io.github.ryunen344.tink"
    version = "0.0.1"

    tasks.withType(KotlinCompile::class) {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
            languageVersion = "1.8"
            apiVersion = "1.8"
        }
    }
}
