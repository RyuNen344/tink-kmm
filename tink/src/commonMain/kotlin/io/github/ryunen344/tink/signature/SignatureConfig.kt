package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.exception.GeneralSecurityException

expect class SignatureConfig constructor() {
    @Throws(GeneralSecurityException::class)
    fun register()
}
