PODS_ROOT = ./Framework
STUB_ROOT = ./TinkStub

.PHONY: clean
clean:
	@rm -rf $(PODS_ROOT)/Tink.xcframework
	@rm -rf "$(STUB_ROOT)bazel-*"

.PHONY: bootstrap-submodule
bootstrap-submodule:
	@git submodule init
	@git submodule update

.PHONY: build-bazel
build-bazel: bootstrap-submodule
	@cd $(STUB_ROOT)/objc && bazel build --disk_cache=./../../build/bazel --apple_platform_type=ios --xcode_version="$(cat ./../.xcode-version)" //:Tink && cd ../..

.PHONY: archive
archive: build-bazel
	@unzip $(STUB_ROOT)/objc/bazel-bin/Tink.xcframework.zip -d $(PODS_ROOT)
