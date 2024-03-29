package io.github.ryunen344.tink.util

import kotlinx.cinterop.MemScope
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.value
import platform.Foundation.NSError

internal inline fun <T> memScopedInstance(
    block: MemScope.(error: ObjCObjectVar<NSError?>) -> T,
    onError: (NSError) -> Unit = {},
): T = memScoped {
    val error = alloc<ObjCObjectVar<NSError?>>()
    val instance = block(error)
    error.value?.let(onError)
    instance
}
