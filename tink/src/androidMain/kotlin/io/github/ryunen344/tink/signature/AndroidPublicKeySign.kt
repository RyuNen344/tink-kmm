package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativePublicKeySign = com.google.crypto.tink.PublicKeySign

class AndroidPublicKeySign(private val native: NativePublicKeySign) : PublicKeySign {
    constructor(handle: com.google.crypto.tink.KeysetHandle) :
        this(handle.getPrimitive(NativePublicKeySign::class.java))

    @Throws(GeneralSecurityException::class)
    override fun sign(data: ByteArray): ByteArray =
        native.sign(data)
}
