package io.github.ryunen344.tink.mac

import io.github.ryunen344.tink.exception.GeneralSecurityException

class MacConfig {
    companion object
}

@Throws(GeneralSecurityException::class)
expect fun MacConfig.Companion.register()
