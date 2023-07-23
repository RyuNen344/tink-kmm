package io.github.ryunen344.tink.signature

import com.google.crypto.tink.TINKKeysetHandle
import com.google.crypto.tink.TINKPublicKeySignFactory
import com.google.crypto.tink.TINKPublicKeySignProtocol
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toByteArray
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class DarwinPublicKeySign(private val native: TINKPublicKeySignProtocol) : PublicKeySign {

    @Throws(GeneralSecurityException::class)
    constructor(handle: TINKKeysetHandle) : this(
        memScopedInstance(
            block = {
                TINKPublicKeySignFactory.primitiveWithKeysetHandle(handle, it.ptr) ?: throw GeneralSecurityException(
                    cause = it.value?.asThrowable()
                )
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )
    )

    @Throws(GeneralSecurityException::class)
    override fun sign(data: ByteArray): ByteArray = memScopedInstance(
        block = {
            native.signatureForData(data.toNSData(), it.ptr)?.toByteArray()
                ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
    )
}
