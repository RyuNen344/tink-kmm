package io.github.ryunen344.tink.config

import com.google.crypto.tink.TINKAllConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import kotlinx.cinterop.ptr

@Throws(GeneralSecurityException::class)
actual fun TinkConfig.Companion.register(): Unit =
    memScopedInstance(
        block = { com.google.crypto.tink.TINKConfig.registerConfig(config = TINKAllConfig(it.ptr), error = it.ptr) },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
    )
