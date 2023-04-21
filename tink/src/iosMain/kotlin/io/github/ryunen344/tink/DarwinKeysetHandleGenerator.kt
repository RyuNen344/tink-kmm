package io.github.ryunen344.tink

import com.google.crypto.tink.TINKKeysetHandle
import com.google.crypto.tink.create
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.DarwinAead
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.generateNew(keyTemplate: KeyTemplate): KeysetHandle = memScopedInstance(
    block = { KeysetHandle(keyTemplate = keyTemplate, error = it.ptr) },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.read(
    reader: KeysetReader,
    aead: Aead,
): KeysetHandle = memScopedInstance(
    block = { KeysetHandle(keysetReader = reader, andKey = (aead as DarwinAead).native, error = it.ptr) },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.readClearText(reader: KeysetReader): KeysetHandle = memScopedInstance(
    block = {
        TINKKeysetHandle.create(cleartextKeysetHandleWithKeysetReader = reader, error = it.ptr)
            ?: throw GeneralSecurityException(cause = it.value?.asThrowable())
    },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.readNoSecret(keyset: ByteArray): KeysetHandle = memScopedInstance(
    block = { KeysetHandle(noSecretKeyset = keyset.toNSData(), error = it.ptr) },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)
