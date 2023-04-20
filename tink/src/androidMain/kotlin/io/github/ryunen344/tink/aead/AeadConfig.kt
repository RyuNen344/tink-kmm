package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeAeadConfig = com.google.crypto.tink.aead.AeadConfig

@Throws(GeneralSecurityException::class)
actual fun AeadConfig.Companion.register() = NativeAeadConfig.register()
