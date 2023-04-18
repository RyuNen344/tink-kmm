package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.exception.GeneralSecurityException

expect class AeadConfig constructor() {
    @Throws(GeneralSecurityException::class)
    fun register()
}
