package io.github.ryunen344.tink.mac

import com.google.crypto.tink.TINKConfig
import com.google.crypto.tink.TINKMacConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import kotlinx.cinterop.ptr

@Throws(GeneralSecurityException::class)
actual fun MacConfig.Companion.register(): Unit =
    memScopedInstance(
        block = { TINKConfig.registerConfig(TINKMacConfig(it.ptr), it.ptr) },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
    )
