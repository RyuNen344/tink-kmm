package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeDeterministicAead = com.google.crypto.tink.DeterministicAead

class AndroidDeterministicAead(
    private val native: NativeDeterministicAead,
) : DeterministicAead, NativeDeterministicAead by native {

    constructor(handle: com.google.crypto.tink.KeysetHandle) :
        this(handle.getPrimitive(NativeDeterministicAead::class.java))

    @Throws(GeneralSecurityException::class)
    override fun encryptDeterministically(plaintext: ByteArray, associatedData: ByteArray): ByteArray =
        native.encryptDeterministically(plaintext, associatedData)

    @Throws(GeneralSecurityException::class)
    override fun decryptDeterministically(ciphertext: ByteArray, associatedData: ByteArray): ByteArray =
        native.decryptDeterministically(ciphertext, associatedData)
}
