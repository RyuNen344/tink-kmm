package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.exception.GeneralSecurityException

expect class AeadConfig {
    @Throws(GeneralSecurityException::class)
    fun register()
}
