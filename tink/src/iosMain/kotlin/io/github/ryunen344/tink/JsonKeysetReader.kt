package io.github.ryunen344.tink

import com.google.crypto.tink.TINKJSONKeysetReader
import io.github.ryunen344.tink.exception.JsonException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.ptr

actual class JsonKeysetReader actual constructor(bytes: ByteArray) : KeysetReader(
    native = memScopedInstance(
        block = { TINKJSONKeysetReader(serializedKeyset = bytes.toNSData(), error = it.ptr) },
        onError = { throw JsonException(cause = it.asThrowable()) }
    )
) {
    actual constructor(json: String) : this(json.encodeToByteArray())
}
