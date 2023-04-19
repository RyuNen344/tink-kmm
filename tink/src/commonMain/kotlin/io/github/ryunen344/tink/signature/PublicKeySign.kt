package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.TinkPrimitive
import io.github.ryunen344.tink.exception.GeneralSecurityException

interface PublicKeySign : TinkPrimitive {
    @Throws(GeneralSecurityException::class)
    fun sign(data: ByteArray): ByteArray
}
