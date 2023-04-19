package io.github.ryunen344.tink.aead

import com.google.crypto.tink.TINKAeadFactory
import com.google.crypto.tink.TINKAeadProtocol
import com.google.crypto.tink.TINKKeysetHandle
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toByteArray
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

class DarwinAead(private val native: TINKAeadProtocol) : Aead {

    @Throws(GeneralSecurityException::class)
    constructor(handle: TINKKeysetHandle) : this(
        memScopedInstance(
            block = {
                TINKAeadFactory.primitiveWithKeysetHandle(handle, it.ptr)
                    ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )
    )

    @Throws(GeneralSecurityException::class)
    override fun encrypt(plaintext: ByteArray, associatedData: ByteArray): ByteArray = memScopedInstance(
        block = {
            native.encrypt(plaintext.toNSData(), associatedData.toNSData(), it.ptr)?.toByteArray()
                ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
    )

    @Throws(GeneralSecurityException::class)
    override fun decrypt(ciphertext: ByteArray, associatedData: ByteArray): ByteArray = memScopedInstance(
        block = {
            native.decrypt(ciphertext.toNSData(), associatedData.toNSData(), it.ptr)?.toByteArray()
                ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
    )
}
