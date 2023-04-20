package io.github.ryunen344.tink.daead

import com.google.crypto.tink.TINKConfig
import com.google.crypto.tink.TINKDeterministicAeadConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

@Throws(GeneralSecurityException::class)
actual fun DeterministicAeadConfig.Companion.register(): Unit =
    memScopedInstance(
        block = { TINKConfig.registerConfig(config = TINKDeterministicAeadConfig(it.ptr), error = it.ptr) },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
    )
