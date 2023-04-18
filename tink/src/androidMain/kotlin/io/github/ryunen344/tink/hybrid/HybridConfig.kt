package io.github.ryunen344.tink.hybrid

import com.google.crypto.tink.hybrid.HybridConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException

actual class HybridConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        HybridConfig.register()
    }
}
