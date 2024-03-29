package io.github.ryunen344.tink.hybrid

import com.google.crypto.tink.TINKHybridEncryptFactory
import com.google.crypto.tink.TINKHybridEncryptProtocol
import com.google.crypto.tink.TINKKeysetHandle
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toByteArray
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

class DarwinHybridEncrypt(private val native: TINKHybridEncryptProtocol) : HybridEncrypt {

    @Throws(GeneralSecurityException::class)
    constructor(handle: TINKKeysetHandle) : this(
        memScopedInstance(
            block = {
                TINKHybridEncryptFactory.primitiveWithKeysetHandle(handle, it.ptr)
                    ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )
    )

    @Throws(GeneralSecurityException::class)
    override fun encrypt(plaintext: ByteArray, contextInfo: ByteArray): ByteArray = memScopedInstance(
        block = {
            native.encrypt(plaintext.toNSData(), contextInfo.toNSData(), it.ptr)?.toByteArray()
                ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
    )
}
