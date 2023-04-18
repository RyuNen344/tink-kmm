package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.exception.GeneralSecurityException

expect class HybridConfig {
    @Throws(GeneralSecurityException::class)
    fun register()
}
