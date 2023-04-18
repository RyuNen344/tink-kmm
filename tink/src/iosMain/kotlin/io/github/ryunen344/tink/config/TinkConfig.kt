package io.github.ryunen344.tink.config

import com.google.crypto.tink.TINKAllConfig
import com.google.crypto.tink.TINKConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.Foundation.NSError

@Deprecated("Use per-primitive configs, e.g., AeadConfig, HybridConfig, etc.")
actual class TinkConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        memScoped {
            val error = alloc<ObjCObjectVar<NSError?>>()
            val allConfig = TINKAllConfig(error.ptr)
            TINKConfig.registerConfig(config = allConfig, error = error.ptr)
        }
    }
}
