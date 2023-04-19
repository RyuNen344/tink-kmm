package io.github.ryunen344.tink.mac

import com.google.crypto.tink.mac.MacConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException

actual class MacConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        MacConfig.register()
    }
}
