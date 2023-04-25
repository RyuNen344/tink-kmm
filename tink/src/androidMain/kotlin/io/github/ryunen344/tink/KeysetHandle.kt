package io.github.ryunen344.tink

import com.google.crypto.tink.CleartextKeysetHandle
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.AndroidAead
import io.github.ryunen344.tink.aead.NativeAead
import io.github.ryunen344.tink.daead.AndroidDeterministicAead
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.exception.IOException
import io.github.ryunen344.tink.hybrid.AndroidHybridDecrypt
import io.github.ryunen344.tink.hybrid.AndroidHybridEncrypt
import io.github.ryunen344.tink.hybrid.HybridDecrypt
import io.github.ryunen344.tink.hybrid.HybridEncrypt
import io.github.ryunen344.tink.mac.AndroidMac
import io.github.ryunen344.tink.mac.Mac
import io.github.ryunen344.tink.signature.AndroidPublicKeySign
import io.github.ryunen344.tink.signature.AndroidPublicKeyVerify
import io.github.ryunen344.tink.signature.PublicKeySign
import io.github.ryunen344.tink.signature.PublicKeyVerify
import kotlin.reflect.KClass

actual typealias KeysetHandle = com.google.crypto.tink.KeysetHandle

@Throws(GeneralSecurityException::class, IOException::class)
actual fun KeysetHandle.write(writer: KeysetWriter, aead: Aead) = writeWithAssociatedData(writer, aead, ByteArray(0))

@Throws(GeneralSecurityException::class, IOException::class)
actual fun KeysetHandle.writeWithAssociatedData(writer: KeysetWriter, aead: Aead, associatedData: ByteArray) =
    writeWithAssociatedData(writer, aead as NativeAead, associatedData)

@Throws(GeneralSecurityException::class, IOException::class)
actual fun KeysetHandle.writeNoSecret(writer: KeysetWriter) = writeNoSecret(writer)

@Throws(GeneralSecurityException::class, IOException::class)
actual fun KeysetHandle.writeCleartext(writer: KeysetWriter) = CleartextKeysetHandle.write(this, writer)

@Suppress("UNCHECKED_CAST")
@Throws(GeneralSecurityException::class)
actual fun <P : TinkPrimitive> KeysetHandle.getPrimitive(kClass: KClass<P>): P {
    val primitive = when (kClass) {
        Aead::class -> AndroidAead(this)
        DeterministicAead::class -> AndroidDeterministicAead(this)
        Mac::class -> AndroidMac(this)
        PublicKeySign::class -> AndroidPublicKeySign(this)
        PublicKeyVerify::class -> AndroidPublicKeyVerify(this)
        HybridEncrypt::class -> AndroidHybridEncrypt(this)
        HybridDecrypt::class -> AndroidHybridDecrypt(this)
        else -> throw GeneralSecurityException("not supported $kClass")
    }

    return primitive as P
}

@Throws(GeneralSecurityException::class)
actual fun KeysetHandle.publicKeysetHandle(): KeysetHandle = publicKeysetHandle
