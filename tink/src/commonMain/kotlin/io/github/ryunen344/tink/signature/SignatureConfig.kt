package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.exception.GeneralSecurityException

class SignatureConfig {
    companion object
}

@Throws(GeneralSecurityException::class)
expect fun SignatureConfig.Companion.register()
