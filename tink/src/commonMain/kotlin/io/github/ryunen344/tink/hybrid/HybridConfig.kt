package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.exception.GeneralSecurityException

class HybridConfig {
    companion object
}

@Throws(GeneralSecurityException::class)
expect fun HybridConfig.Companion.register()
