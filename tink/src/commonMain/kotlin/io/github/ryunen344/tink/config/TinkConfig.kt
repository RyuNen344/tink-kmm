package io.github.ryunen344.tink.config

import io.github.ryunen344.tink.exception.GeneralSecurityException

@Deprecated("Use per-primitive configs, e.g., AeadConfig, HybridConfig, etc.")
class TinkConfig {
    companion object
}

@Deprecated("Use per-primitive configs, e.g., AeadConfig, HybridConfig, etc.")
@Throws(GeneralSecurityException::class)
expect fun TinkConfig.Companion.register()
