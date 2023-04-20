package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.exception.GeneralSecurityException

class DeterministicAeadConfig {
    companion object
}

@Throws(GeneralSecurityException::class)
expect fun DeterministicAeadConfig.Companion.register()
