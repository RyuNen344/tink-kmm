package io.github.ryunen344.tink.signature

import com.google.crypto.tink.TINKConfig
import com.google.crypto.tink.TINKSignatureConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import kotlinx.cinterop.ptr

@Throws(GeneralSecurityException::class)
actual fun SignatureConfig.Companion.register(): Unit =
    memScopedInstance(
        block = { TINKConfig.registerConfig(config = TINKSignatureConfig(it.ptr), error = it.ptr) },
        onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
    )
