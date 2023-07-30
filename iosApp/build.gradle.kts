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
        // "SYMROOT=${buildDir}/sym/Debug-iphonesimulator",
        // "BUILD_ROOT=${buildDir}/sym",
        // "TARGET_BUILD_DIR=${buildDir}/sym",
        // "BUILT_PRODUCTS_DIR=${buildDir}/sym",
        "CONFIGURATION_BUILD_DIR=\$SYMROOT",
        // "TEST_HOST=${buildDir}/sym/iosApp.app/iosApp",
    )
}

// xcodebuild test -project iosApp/iosApp.xcodeproj -scheme iosApp -destination 'platform=iOS Simulator,name=iPhone 14 Pro,OS=16.4'
// xcodebuild test -scheme iosApp -destination platform=iOS Simulator -configuration Debug ,id=692ED55A-EFCD-4DC0-AB50-A01DA7FD0C7B -derivedDataPath /Users/miyoshibunjiro/Documents/00_ryunen/tink-kmm/iosApp/build/derivedData DSTROOT=/Users/miyoshibunjiro/Documents/00_ryunen/tink-kmm/iosApp/build/dst OBJROOT=/Users/miyoshibunjiro/Documents/00_ryunen/tink-kmm/iosApp/build/obj SYMROOT=/Users/miyoshibunjiro/Documents/00_ryunen/tink-kmm/iosApp/build/sym SHARED_PRECOMPS_DIR=/Users/miyoshibunjiro/Documents/00_ryunen/tink-kmm/iosApp/build/shared -enableCodeCoverage yes COMPILER_INDEX_STORE_ENABLE=NO -disable-concurrent-destination-testing
