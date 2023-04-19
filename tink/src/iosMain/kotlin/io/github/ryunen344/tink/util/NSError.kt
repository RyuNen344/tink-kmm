package io.github.ryunen344.tink.util

import platform.Foundation.NSError

internal fun NSError.asThrowable(): Throwable {
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
