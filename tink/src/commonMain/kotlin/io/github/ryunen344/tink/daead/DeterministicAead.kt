package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.TinkPrimitive
import io.github.ryunen344.tink.exception.GeneralSecurityException

interface DeterministicAead : TinkPrimitive {
    @Throws(GeneralSecurityException::class)
    fun encryptDeterministically(plaintext: ByteArray, associatedData: ByteArray): ByteArray

    @Throws(GeneralSecurityException::class)
    fun decryptDeterministically(ciphertext: ByteArray, associatedData: ByteArray): ByteArray
}
