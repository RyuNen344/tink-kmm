package io.github.ryunen344.tink

import com.google.crypto.tink.TINKAeadConfig
import com.google.crypto.tink.TINKConfig
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.Foundation.NSError

actual class TinkConfig {
    actual fun register() {
        memScoped {
            val error = alloc<ObjCObjectVar<NSError?>>()
            val aeadConfig = TINKAeadConfig(error.ptr)
            TINKConfig.registerConfig(config = aeadConfig, error = error.ptr)
        }
    }
}
