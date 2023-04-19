package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.TinkPrimitive
import io.github.ryunen344.tink.exception.GeneralSecurityException

interface PublicKeyVerify : TinkPrimitive {
    @Throws(GeneralSecurityException::class)
    fun verify(signature: ByteArray, data: ByteArray)
}
