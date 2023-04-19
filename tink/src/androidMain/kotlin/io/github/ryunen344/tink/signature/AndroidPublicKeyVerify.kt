package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativePublicKeyVerify = com.google.crypto.tink.PublicKeyVerify

class AndroidPublicKeyVerify(private val native: NativePublicKeyVerify) : PublicKeyVerify {
    constructor(handle: com.google.crypto.tink.KeysetHandle) :
        this(handle.getPrimitive(NativePublicKeyVerify::class.java))

    @Throws(GeneralSecurityException::class)
    override fun verify(signature: ByteArray, data: ByteArray) =
        native.verify(signature, data)
}
