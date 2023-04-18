package io.github.ryunen344.tink.signature

import com.google.crypto.tink.signature.SignatureConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException

actual class SignatureConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        SignatureConfig.register()
    }
}
