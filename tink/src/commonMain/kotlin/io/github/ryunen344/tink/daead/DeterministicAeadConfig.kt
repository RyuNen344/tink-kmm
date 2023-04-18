package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.exception.GeneralSecurityException

expect class DeterministicAeadConfig constructor() {
    @Throws(GeneralSecurityException::class)
    fun register()
}
