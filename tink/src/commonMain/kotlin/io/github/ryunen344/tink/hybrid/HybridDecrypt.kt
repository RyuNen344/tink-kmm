package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.TinkPrimitive
import io.github.ryunen344.tink.exception.GeneralSecurityException

interface HybridDecrypt : TinkPrimitive {
    @Throws(GeneralSecurityException::class)
    fun decrypt(ciphertext: ByteArray, contextInfo: ByteArray): ByteArray
}
