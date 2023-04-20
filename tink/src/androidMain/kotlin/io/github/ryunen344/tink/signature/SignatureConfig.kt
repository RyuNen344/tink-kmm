package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeSignatureConfig = com.google.crypto.tink.signature.SignatureConfig

@Throws(GeneralSecurityException::class)
actual fun SignatureConfig.Companion.register() = NativeSignatureConfig.register()
