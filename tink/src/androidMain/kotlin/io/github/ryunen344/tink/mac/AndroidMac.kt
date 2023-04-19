package io.github.ryunen344.tink.mac

import io.github.ryunen344.tink.exception.GeneralSecurityException

internal typealias NativeMac = com.google.crypto.tink.Mac

class AndroidMac(private val native: NativeMac) : Mac {
    constructor(handle: com.google.crypto.tink.KeysetHandle) :
        this(handle.getPrimitive(NativeMac::class.java))

    @Throws(GeneralSecurityException::class)
    override fun computeMac(data: ByteArray): ByteArray =
        native.computeMac(data)

    @Throws(GeneralSecurityException::class)
    override fun verifyMac(mac: ByteArray, data: ByteArray) =
        native.verifyMac(mac, data)
}
