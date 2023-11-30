package io.github.ryunen344.tink.mac

import com.google.crypto.tink.TINKKeysetHandle
import com.google.crypto.tink.TINKMacFactory
import com.google.crypto.tink.TINKMacProtocol
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toByteArray
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

class DarwinMac(private val native: TINKMacProtocol) : Mac {

    @Throws(GeneralSecurityException::class)
    constructor(handle: TINKKeysetHandle) : this(
        memScopedInstance(
            block = {
                TINKMacFactory.primitiveWithKeysetHandle(handle, it.ptr)
                    ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
            },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
        )
    )

    @Throws(GeneralSecurityException::class)
    override fun computeMac(data: ByteArray): ByteArray = memScopedInstance(
        block = {
            native.computeMacForData(data.toNSData(), it.ptr)?.toByteArray()
                ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
    )

    @Throws(GeneralSecurityException::class)
    override fun verifyMac(mac: ByteArray, data: ByteArray): Unit = memScopedInstance(
        block = {
            val verified = native.verifyMac(mac.toNSData(), data.toNSData(), it.ptr)
            if (!verified) throw GeneralSecurityException("invalid MAC")
        },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
    )
}
