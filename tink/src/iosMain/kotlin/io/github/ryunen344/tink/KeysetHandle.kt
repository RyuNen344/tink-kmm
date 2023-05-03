package io.github.ryunen344.tink

import com.google.crypto.tink.serializedKeyset
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.DarwinAead
import io.github.ryunen344.tink.daead.DarwinDeterministicAead
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.exception.IOException
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
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import kotlin.reflect.KClass

actual typealias KeysetHandle = com.google.crypto.tink.TINKKeysetHandle

@Throws(GeneralSecurityException::class, IOException::class)
actual fun KeysetHandle.writeNoSecret(writer: KeysetWriter) = memScopedInstance(
    block = { writer.value = serializedKeysetNoSecret(it.ptr) },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@Throws(GeneralSecurityException::class, IOException::class)
actual fun KeysetHandle.writeCleartext(writer: KeysetWriter) {
    writer.value = serializedKeyset()
}

@Suppress("CAST_NEVER_SUCCEEDS")
inline fun String.toNSString() = this as NSString

inline fun String.toNSData() = toNSString().toNSData()

@Suppress("CAST_NEVER_SUCCEEDS")
inline fun NSString.toKString() = this as String

inline fun NSString.toNSData() = dataUsingEncoding(NSUTF8StringEncoding)

inline fun NSData.toNSString() = NSString.create(data = this, encoding = NSUTF8StringEncoding)

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
