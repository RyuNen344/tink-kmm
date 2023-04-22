package io.github.ryunen344.tink.exception

actual class JsonException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
