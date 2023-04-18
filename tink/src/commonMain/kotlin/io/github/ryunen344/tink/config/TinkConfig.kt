package io.github.ryunen344.tink.config

import io.github.ryunen344.tink.exception.GeneralSecurityException

@Deprecated("Use per-primitive configs, e.g., AeadConfig, HybridConfig, etc.")
expect class TinkConfig {
    @Throws(GeneralSecurityException::class)
    fun register()
}
