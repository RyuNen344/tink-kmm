package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.TinkPrimitive
import io.github.ryunen344.tink.exception.GeneralSecurityException

interface Aead : TinkPrimitive {
    @Throws(GeneralSecurityException::class)
    fun encrypt(plaintext: ByteArray, associatedData: ByteArray): ByteArray

    @Throws(GeneralSecurityException::class)
    fun decrypt(ciphertext: ByteArray, associatedData: ByteArray): ByteArray
}
