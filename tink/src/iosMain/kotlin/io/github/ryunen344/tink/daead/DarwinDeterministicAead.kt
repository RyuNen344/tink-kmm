package io.github.ryunen344.tink.daead

import com.google.crypto.tink.TINKDeterministicAeadFactory
import com.google.crypto.tink.TINKDeterministicAeadProtocol
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
class DarwinDeterministicAead(private val native: TINKDeterministicAeadProtocol) : DeterministicAead {

    @Throws(GeneralSecurityException::class)
    constructor(handle: TINKKeysetHandle) : this(
        memScopedInstance(
            block = {
                TINKDeterministicAeadFactory.primitiveWithKeysetHandle(handle, it.ptr)
                    ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )
    )

    @Throws(GeneralSecurityException::class)
    override fun encryptDeterministically(plaintext: ByteArray, associatedData: ByteArray): ByteArray =
        memScopedInstance(
            block = {
                native.encryptDeterministically(plaintext.toNSData(), associatedData.toNSData(), it.ptr)?.toByteArray()
                    ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )

    @Throws(GeneralSecurityException::class)
    override fun decryptDeterministically(ciphertext: ByteArray, associatedData: ByteArray): ByteArray =
        memScopedInstance(
            block = {
                native.decryptDeterministically(ciphertext.toNSData(), associatedData.toNSData(), it.ptr)?.toByteArray()
                    ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) },
        )
}
