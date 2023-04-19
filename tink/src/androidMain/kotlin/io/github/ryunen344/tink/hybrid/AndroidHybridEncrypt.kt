package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeHybridEncrypt = com.google.crypto.tink.HybridEncrypt

class AndroidHybridEncrypt(private val native: NativeHybridEncrypt) : HybridEncrypt {
    constructor(handle: com.google.crypto.tink.KeysetHandle) :
        this(handle.getPrimitive(NativeHybridEncrypt::class.java))

    @Throws(GeneralSecurityException::class)
    override fun encrypt(plaintext: ByteArray, contextInfo: ByteArray): ByteArray =
        native.encrypt(plaintext, contextInfo)
}
