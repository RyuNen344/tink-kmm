package io.github.ryunen344.tink.util

import platform.Foundation.NSError
import kotlin.native.internal.ObjCErrorException

fun NSError.asThrowable(): Throwable {
    val message = buildString {
        append(domain ?: "NSError")
        append("code [$code]")
        userInfo.forEach {
            appendLine("{${it.key} = ${it.value}}")
        }
    }
    return Throwable(
        message = message
    )
}

val Throwable.isNSError: Boolean
    get() = this is ObjCErrorException
