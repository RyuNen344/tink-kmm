package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeHybridDecrypt = com.google.crypto.tink.HybridDecrypt

class AndroidHybridDecrypt(private val native: NativeHybridDecrypt) : HybridDecrypt {
    constructor(handle: com.google.crypto.tink.KeysetHandle) :
        this(handle.getPrimitive(NativeHybridDecrypt::class.java))

    @Throws(GeneralSecurityException::class)
    override fun decrypt(ciphertext: ByteArray, contextInfo: ByteArray): ByteArray =
        native.decrypt(ciphertext, contextInfo)
}
