package io.github.ryunen344.tink.mac

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeMacConfig = com.google.crypto.tink.mac.MacConfig

@Throws(GeneralSecurityException::class)
actual fun MacConfig.Companion.register() = NativeMacConfig.register()
