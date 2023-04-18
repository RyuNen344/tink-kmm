package io.github.ryunen344.tink.daead

import com.google.crypto.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException

actual class DeterministicAeadConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        DeterministicAeadConfig.register()
    }
}
