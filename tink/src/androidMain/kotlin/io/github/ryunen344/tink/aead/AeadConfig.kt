package io.github.ryunen344.tink.aead

import com.google.crypto.tink.aead.AeadConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException

actual class AeadConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        AeadConfig.register()
    }
}
