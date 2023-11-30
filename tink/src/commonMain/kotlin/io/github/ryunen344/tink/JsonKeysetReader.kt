package io.github.ryunen344.tink

import io.github.ryunen344.tink.exception.JsonException

@Suppress("UnusedPrivateProperty")
expect class JsonKeysetReader
@Throws(JsonException::class)
constructor(bytes: ByteArray) : KeysetReader {
    @Throws(JsonException::class)
    constructor(json: String)
}
