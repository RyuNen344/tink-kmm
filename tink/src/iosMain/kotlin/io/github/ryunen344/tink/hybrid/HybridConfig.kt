package io.github.ryunen344.tink.hybrid

import com.google.crypto.tink.TINKConfig
import com.google.crypto.tink.TINKHybridConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

actual class HybridConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        memScopedInstance(
            block = { TINKConfig.registerConfig(config = TINKHybridConfig(it.ptr), error = it.ptr) },
            onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
        )
    }
}
