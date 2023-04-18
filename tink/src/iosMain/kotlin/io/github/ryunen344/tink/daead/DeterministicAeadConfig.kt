package io.github.ryunen344.tink.daead

import com.google.crypto.tink.TINKConfig
import com.google.crypto.tink.TINKDeterministicAeadConfig
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

actual class DeterministicAeadConfig {
    @Throws(GeneralSecurityException::class)
    actual fun register() {
        memScoped {
            val initError = alloc<ObjCObjectVar<NSError?>>()
            val daeadConfig = TINKDeterministicAeadConfig(initError.ptr)
            initError.value?.let { throw GeneralSecurityException(cause = it.asThrowable()) }

            val registerError = alloc<ObjCObjectVar<NSError?>>()
            TINKConfig.registerConfig(config = daeadConfig, error = registerError.ptr)
            registerError.value?.let { throw GeneralSecurityException(cause = it.asThrowable()) }
        }
    }
}
