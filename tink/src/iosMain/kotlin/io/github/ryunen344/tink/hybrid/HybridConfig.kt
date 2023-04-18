package io.github.ryunen344.tink.hybrid

import com.google.crypto.tink.TINKConfig
import com.google.crypto.tink.TINKHybridConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

actual class HybridConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        memScoped {
            val error = alloc<ObjCObjectVar<NSError?>>()
            val hybridConfig = TINKHybridConfig(error.ptr)
            TINKConfig.registerConfig(config = hybridConfig, error = error.ptr)
            error.value?.let { throw GeneralSecurityException(cause = it.asThrowable()) }
        }
    }
}
