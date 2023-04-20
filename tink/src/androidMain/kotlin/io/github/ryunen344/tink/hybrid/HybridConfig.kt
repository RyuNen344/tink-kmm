package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeHybridConfig = com.google.crypto.tink.hybrid.HybridConfig

@Throws(GeneralSecurityException::class)
actual fun HybridConfig.Companion.register() = NativeHybridConfig.register()
