package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeAead = com.google.crypto.tink.Aead

class AndroidAead(private val native: NativeAead) : Aead, NativeAead by native {
    constructor(handle: com.google.crypto.tink.KeysetHandle) :
        this(handle.getPrimitive(NativeAead::class.java))

    @Throws(GeneralSecurityException::class)
    override fun encrypt(plaintext: ByteArray, associatedData: ByteArray): ByteArray =
        native.encrypt(plaintext, associatedData)

    @Throws(GeneralSecurityException::class)
    override fun decrypt(ciphertext: ByteArray, associatedData: ByteArray): ByteArray =
        native.decrypt(ciphertext, associatedData)
}
