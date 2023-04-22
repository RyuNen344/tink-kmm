package io.github.ryunen344.tink

import com.google.crypto.tink.CleartextKeysetHandle
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.NativeAead
import io.github.ryunen344.tink.exception.GeneralSecurityException

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.generateNew(keyTemplate: KeyTemplate): KeysetHandle =
    KeysetHandle.generateNew(keyTemplate)

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.read(
    reader: KeysetReader,
    aead: Aead,
): KeysetHandle = KeysetHandle.read(reader, aead as NativeAead)

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.readClearText(reader: KeysetReader): KeysetHandle =
    CleartextKeysetHandle.read(reader)

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.readNoSecret(keyset: ByteArray): KeysetHandle =
    KeysetHandle.readNoSecret(keyset)
