package io.github.ryunen344.tink.aead

import com.google.crypto.tink.TINKAeadConfig
import com.google.crypto.tink.TINKConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

actual class AeadConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        memScoped {
            val initError = alloc<ObjCObjectVar<NSError?>>()
            val aeadConfig = TINKAeadConfig(initError.ptr)
            initError.value?.let { throw GeneralSecurityException(cause = it.asThrowable()) }

            val registerError = alloc<ObjCObjectVar<NSError?>>()
            TINKConfig.registerConfig(config = aeadConfig, error = registerError.ptr)
            registerError.value?.let { throw GeneralSecurityException(cause = it.asThrowable()) }
        }
    }
}
