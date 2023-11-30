package io.github.ryunen344.tink

import io.github.ryunen344.tink.exception.JsonException

typealias NativeJsonKeysetReader = com.google.crypto.tink.JsonKeysetReader

actual class JsonKeysetReader @Throws(JsonException::class) actual constructor(bytes: ByteArray) :
    KeysetReader(NativeJsonKeysetReader.withBytes(bytes)) {
    @Throws(JsonException::class)
    actual constructor(json: String) : this(json.encodeToByteArray())
}
