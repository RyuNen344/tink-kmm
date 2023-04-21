import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application").version("7.4.2").apply(false)
    id("com.android.library").version("7.4.2").apply(false)
    kotlin("android").version("1.8.20").apply(false)
    kotlin("multiplatform").version("1.8.20").apply(false)
    id("io.gitlab.arturbosch.detekt").version("1.23.0-RC1")
    id("com.louiscad.complete-kotlin").version("1.1.0")
    id("jacoco")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.0-RC1")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.23.0-RC1")
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
                exclude("TinkStub")
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
