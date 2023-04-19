package io.github.ryunen344.tink.config

import com.google.crypto.tink.TINKAllConfig
import com.google.crypto.tink.TINKConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import kotlinx.cinterop.ptr

@Deprecated("Use per-primitive configs, e.g., AeadConfig, HybridConfig, etc.")
actual class TinkConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        memScopedInstance(
            block = { TINKConfig.registerConfig(config = TINKAllConfig(it.ptr), error = it.ptr) },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
        )
    }
}
