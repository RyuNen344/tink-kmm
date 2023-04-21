package io.github.ryunen344.tink

import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.exception.GeneralSecurityException
import kotlin.reflect.KClass

expect class KeysetHandle

@Throws(GeneralSecurityException::class)
expect fun KeysetHandle.writeNoSecret(): ByteArray

@Throws(GeneralSecurityException::class)
expect fun <P : TinkPrimitive> KeysetHandle.getPrimitive(kClass: KClass<P>): P

@Throws(GeneralSecurityException::class)
expect fun KeysetHandle.publicKeysetHandle(): KeysetHandle

class KeysetHandleGenerator {
    companion object
}

@Throws(GeneralSecurityException::class)
expect fun KeysetHandleGenerator.Companion.generateNew(keyTemplate: KeyTemplate): KeysetHandle

@Throws(GeneralSecurityException::class)
expect fun KeysetHandleGenerator.Companion.read(reader: KeysetReader, aead: Aead): KeysetHandle

@Throws(GeneralSecurityException::class)
expect fun KeysetHandleGenerator.Companion.readClearText(reader: KeysetReader): KeysetHandle

@Throws(GeneralSecurityException::class)
expect fun KeysetHandleGenerator.Companion.readNoSecret(keyset: ByteArray): KeysetHandle
