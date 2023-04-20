package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.exception.GeneralSecurityException

class AeadConfig {
    companion object
}

@Throws(GeneralSecurityException::class)
expect fun AeadConfig.Companion.register()
