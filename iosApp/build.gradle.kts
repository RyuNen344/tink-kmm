plugins {
    id("org.openbakery.xcode-plugin")
}

xcodebuild {
    version = File(rootDir, ".xcode-version").readText().trim()
    scheme = "iosApp"
    target = "iosAppTests"
    setProjectFile("iosApp.xcodeproj")
    destination(
        closureOf<org.openbakery.xcode.Destination> {
            platform = "iOS Simulator"
            name = "iPhone 14 Pro"
            os = "16.4"
        }
    )

    additionalParameters = listOf(
        "CONFIGURATION_BUILD_DIR=\$SYMROOT",
    )
}
