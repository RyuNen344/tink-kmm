package io.github.ryunen344.tink.mac

import io.github.ryunen344.tink.exception.GeneralSecurityException

expect class MacConfig constructor() {
    @Throws(GeneralSecurityException::class)
    fun register()
}
