package io.github.ryunen344.tink

import com.google.crypto.tink.aead.AeadConfig

actual class TinkConfig {
    actual fun register() {
        AeadConfig.register()
    }
}
