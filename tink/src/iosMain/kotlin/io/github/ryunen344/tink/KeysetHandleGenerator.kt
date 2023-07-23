package io.github.ryunen344.tink

import com.google.crypto.tink.TINKKeysetHandle
import com.google.crypto.tink.create
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.DarwinAead
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

@OptIn(ExperimentalForeignApi::class)
@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.generateNew(keyTemplate: KeyTemplate): KeysetHandle = memScopedInstance(
    block = {
        runCatching {
            KeysetHandle(keyTemplate = keyTemplate, error = it.ptr)
        }.getOrElse {
            throw GeneralSecurityException(
                message = "No manager for type ${keyTemplate.description} has been registered.",
                cause = it
            )
        }
    },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@OptIn(ExperimentalForeignApi::class)
@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.read(
    reader: KeysetReader,
    aead: Aead,
): KeysetHandle = memScopedInstance(
    block = {
        runCatching {
            KeysetHandle(keysetReader = reader.native, andKey = (aead as DarwinAead).native, error = it.ptr)
        }.getOrElse {
            throw GeneralSecurityException(
                message = "No manager has been registered.",
                cause = it
            )
        }
    },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.readClearText(reader: KeysetReader): KeysetHandle = memScopedInstance(
    block = {
        TINKKeysetHandle.create(cleartextKeysetHandleWithKeysetReader = reader.native, error = it.ptr)
            ?: throw GeneralSecurityException(
                message = "No manager has been registered.",
                cause = it.value?.asThrowable()
            )
    },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)

@OptIn(ExperimentalForeignApi::class)
@Throws(GeneralSecurityException::class)
actual fun KeysetHandleGenerator.Companion.readNoSecret(keyset: ByteArray): KeysetHandle = memScopedInstance(
    block = {
        runCatching {
            KeysetHandle(noSecretKeyset = keyset.toNSData(), error = it.ptr)
        }.getOrElse {
            throw GeneralSecurityException(
                message = "No manager has been registered.",
                cause = it
            )
        }
    },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)
