package io.github.ryunen344.tink

import io.github.ryunen344.tink.exception.JsonException

expect class JsonKeysetReader @Throws(JsonException::class) constructor(bytes: ByteArray) : KeysetReader {
    @Throws(JsonException::class)
    constructor(json: String)
}

