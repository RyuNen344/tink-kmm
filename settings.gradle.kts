enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.openbakery.xcode-plugin") {
                useModule("org.openbakery:xcode-plugin:0.23.1.30")
            }
        }
    }
    repositories {
        google()
        mavenCentral()
        maven(url = "https://openbakery.org/repository/") {
            content {
                includeGroup("org.openbakery")
            }
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // https://youtrack.jetbrains.com/issue/KT-51379
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.enterprise").version("3.14.1")
}

gradleEnterprise {
    if (System.getenv("CI") != null) {
        buildScan {
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
        }
    }
}

rootProject.name = "Tink-KMM"
include(":iosApp")
include(":tink")
