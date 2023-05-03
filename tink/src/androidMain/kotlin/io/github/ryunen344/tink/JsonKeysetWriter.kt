package io.github.ryunen344.tink

import java.io.ByteArrayOutputStream

typealias NativeJsonKeysetWriter = com.google.crypto.tink.JsonKeysetWriter

class JsonKeysetWriter(
    private val os: ByteArrayOutputStream = ByteArrayOutputStream(),
) : KeysetWriter(NativeJsonKeysetWriter.withOutputStream(os)) {
    override fun write(): ByteArray = os.toByteArray()
}
