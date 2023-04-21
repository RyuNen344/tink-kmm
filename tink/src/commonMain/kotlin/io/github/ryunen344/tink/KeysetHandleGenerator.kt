package io.github.ryunen344.tink

import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.exception.GeneralSecurityException

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
