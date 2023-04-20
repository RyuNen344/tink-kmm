package io.github.ryunen344.tink.config

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeTinkConfig = com.google.crypto.tink.config.TinkConfig

@Throws(GeneralSecurityException::class)
actual fun TinkConfig.Companion.register() = NativeTinkConfig.register()
