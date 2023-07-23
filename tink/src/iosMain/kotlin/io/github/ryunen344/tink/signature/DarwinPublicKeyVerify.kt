package io.github.ryunen344.tink.signature

import com.google.crypto.tink.TINKKeysetHandle
import com.google.crypto.tink.TINKPublicKeyVerifyFactory
import com.google.crypto.tink.TINKPublicKeyVerifyProtocol
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class DarwinPublicKeyVerify(private val native: TINKPublicKeyVerifyProtocol) : PublicKeyVerify {

    @Throws(GeneralSecurityException::class)
    constructor(handle: TINKKeysetHandle) : this(
        memScopedInstance(
            block = {
                TINKPublicKeyVerifyFactory.primitiveWithKeysetHandle(handle, it.ptr) ?: throw GeneralSecurityException(
                    cause = it.value?.asThrowable()
                )
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )
    )

    @Throws(GeneralSecurityException::class)
    override fun verify(signature: ByteArray, data: ByteArray) = memScopedInstance(
        block = {
            val verified = native.verifySignature(signature.toNSData(), data.toNSData(), it.ptr)
            if (!verified) throw GeneralSecurityException("Invalid signature")
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
    )
}
