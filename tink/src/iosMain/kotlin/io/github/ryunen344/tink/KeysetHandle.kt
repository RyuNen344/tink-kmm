package io.github.ryunen344.tink

import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.DarwinAead
import io.github.ryunen344.tink.daead.DarwinDeterministicAead
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.hybrid.DarwinHybridDecrypt
import io.github.ryunen344.tink.hybrid.DarwinHybridEncrypt
import io.github.ryunen344.tink.hybrid.HybridDecrypt
import io.github.ryunen344.tink.hybrid.HybridEncrypt
import io.github.ryunen344.tink.mac.DarwinMac
import io.github.ryunen344.tink.mac.Mac
import io.github.ryunen344.tink.signature.DarwinPublicKeySign
import io.github.ryunen344.tink.signature.DarwinPublicKeyVerify
import io.github.ryunen344.tink.signature.PublicKeySign
import io.github.ryunen344.tink.signature.PublicKeyVerify
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toByteArray
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.reflect.KClass

actual typealias KeysetHandle = com.google.crypto.tink.TINKKeysetHandle

@Throws(GeneralSecurityException::class)
actual fun KeysetHandle.writeNoSecret(): ByteArray = memScopedInstance(
    block = { serializedKeysetNoSecret(it.ptr).toByteArray() },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@Suppress("UNCHECKED_CAST")
@Throws(GeneralSecurityException::class)
actual fun <P : TinkPrimitive> KeysetHandle.getPrimitive(kClass: KClass<P>): P {
    val primitive = when (kClass) {
        Aead::class -> DarwinAead(this)
        DeterministicAead::class -> DarwinDeterministicAead(this)
        Mac::class -> DarwinMac(this)
        PublicKeySign::class -> DarwinPublicKeySign(this)
        PublicKeyVerify::class -> DarwinPublicKeyVerify(this)
        HybridEncrypt::class -> DarwinHybridEncrypt(this)
        HybridDecrypt::class -> DarwinHybridDecrypt(this)
        else -> throw GeneralSecurityException("not supported $kClass")
    }
    return primitive as P
}

@Throws(GeneralSecurityException::class)
actual fun KeysetHandle.publicKeysetHandle(): KeysetHandle = memScopedInstance(
    block = {
        KeysetHandle.publicKeysetHandleWithHandle(this@publicKeysetHandle, it.ptr)
            ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
    },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)
