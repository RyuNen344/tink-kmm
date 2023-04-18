PROJECT_ROOT = $(cd $(dirname $0); cd ..; pwd)
PODS_ROOT = ./Pods
PODS_PROJECT = $(PODS_ROOT)/Pods.xcodeproj
SYMROOT = $(PODS_ROOT)/Build
IPHONEOS_DEPLOYMENT_TARGET = 9.0

.PHONY: clean
clean:
	@rm -rf Pods
	@rm -rf Tink.xcframework

.PHONY: bootstrap-cocoapods
bootstrap-cocoapods: clean
	@bundle install
	@bundle exec pod install

.PHONY: build-cocoapods
build-cocoapods: bootstrap-cocoapods
	@xcodebuild -project "$(PODS_PROJECT)" \
		-sdk iphoneos \
		-configuration Release \
		-alltargets \
		ONLY_ACTIVE_ARCH=NO \
		ENABLE_TESTABILITY=NO \
		SYMROOT="$(SYMROOT)" \
		CLANG_ENABLE_MODULE_DEBUGGING=NO \
		BITCODE_GENERATION_MODE=bitcode \
		IPHONEOS_DEPLOYMENT_TARGET="$(IPHONEOS_DEPLOYMENT_TARGET)" | bundle exec xcpretty -c
	@xcodebuild -project "$(PODS_PROJECT)" \
		-sdk iphonesimulator \
		-configuration Release \
		-alltargets \
		ONLY_ACTIVE_ARCH=NO \
		ENABLE_TESTABILITY=NO \
		SYMROOT="$(SYMROOT)" \
		CLANG_ENABLE_MODULE_DEBUGGING=NO \
		BITCODE_GENERATION_MODE=bitcode \
		IPHONEOS_DEPLOYMENT_TARGET="$(IPHONEOS_DEPLOYMENT_TARGET)" | bundle exec xcpretty -c

.PHONY: archive
archive: build-cocoapods
	@xcodebuild -create-xcframework \
		-framework Pods/Pods/Build/Release-iphonesimulator/Tink/Tink.framework \
		-framework Pods/Pods/Build/Release-iphoneos/Tink/Tink.framework \
		-output Tink.xcframework | bundle exec xcpretty -c

.PHONY: check
check:
	xcrun lipo -info Pods/Tink/Frameworks/Tink.framework/Tink
