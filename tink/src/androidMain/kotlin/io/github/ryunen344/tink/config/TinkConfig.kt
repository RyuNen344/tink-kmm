package io.github.ryunen344.tink.config

import com.google.crypto.tink.config.TinkConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException

@Deprecated("Use per-primitive configs, e.g., AeadConfig, HybridConfig, etc.")
actual class TinkConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        TinkConfig.register()
    }
}
