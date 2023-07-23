package io.github.ryunen344.tink.hybrid

import com.google.crypto.tink.TINKHybridDecryptFactory
import com.google.crypto.tink.TINKHybridDecryptProtocol
import com.google.crypto.tink.TINKKeysetHandle
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
class DarwinHybridDecrypt(private val native: TINKHybridDecryptProtocol) : HybridDecrypt {

    @Throws(GeneralSecurityException::class)
    constructor(handle: TINKKeysetHandle) : this(
        memScopedInstance(
            block = {
                TINKHybridDecryptFactory.primitiveWithKeysetHandle(handle, it.ptr) ?: throw GeneralSecurityException(
                    cause = it.value?.asThrowable()
                )
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )
    )

    @Throws(GeneralSecurityException::class)
    override fun decrypt(ciphertext: ByteArray, contextInfo: ByteArray): ByteArray = memScopedInstance(
        block = {
            native.decrypt(ciphertext.toNSData(), contextInfo.toNSData(), it.ptr)?.toByteArray()
                ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
    )
}
