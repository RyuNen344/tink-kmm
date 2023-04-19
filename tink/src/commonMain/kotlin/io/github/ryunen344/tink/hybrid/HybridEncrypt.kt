package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.TinkPrimitive
import io.github.ryunen344.tink.exception.GeneralSecurityException

interface HybridEncrypt : TinkPrimitive {
    @Throws(GeneralSecurityException::class)
    fun encrypt(plaintext: ByteArray, contextInfo: ByteArray): ByteArray
}
