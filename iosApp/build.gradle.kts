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

    // xcode-pluginがSYMROOTをいじるためCONFIGURATION_BUILD_DIRをSYMROOTと揃えることで帳尻を合わせる
    additionalParameters = listOf(
        "CONFIGURATION_BUILD_DIR=\$SYMROOT",
    )
}

tasks.withType<org.openbakery.XcodeBuildTask>().configureEach {
    dependsOn(":tink:assembleTinKMMDebugXCFramework")
}

tasks.withType<org.openbakery.XcodeTestTask>().configureEach {
    dependsOn(":tink:assembleTinKMMDebugXCFramework")
}
