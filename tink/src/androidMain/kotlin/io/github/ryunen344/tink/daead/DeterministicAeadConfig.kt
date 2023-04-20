package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeDeterministicAeadConfig = com.google.crypto.tink.daead.DeterministicAeadConfig

@Throws(GeneralSecurityException::class)
actual fun DeterministicAeadConfig.Companion.register() = NativeDeterministicAeadConfig.register()
