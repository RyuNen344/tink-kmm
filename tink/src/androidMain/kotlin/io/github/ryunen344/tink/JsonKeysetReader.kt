package io.github.ryunen344.tink

typealias NativeJsonKeysetReader = com.google.crypto.tink.JsonKeysetReader

actual class JsonKeysetReader actual constructor(bytes: ByteArray) :
    KeysetReader(NativeJsonKeysetReader.withBytes(bytes)) {
    actual constructor(json: String) : this(json.encodeToByteArray())
}
