package io.github.ryunen344.tink.mac

import io.github.ryunen344.tink.TinkPrimitive
import io.github.ryunen344.tink.exception.GeneralSecurityException

interface Mac : TinkPrimitive {
    @Throws(GeneralSecurityException::class)
    fun computeMac(data: ByteArray): ByteArray

    @Throws(GeneralSecurityException::class)
    fun verifyMac(mac: ByteArray, data: ByteArray)
}
