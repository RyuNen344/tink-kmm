package io.github.ryunen344.tink

import java.io.ByteArrayOutputStream

typealias NativeJsonKeysetWriter = com.google.crypto.tink.JsonKeysetWriter

actual class JsonKeysetWriter(
    private val os: ByteArrayOutputStream = ByteArrayOutputStream(),
) : KeysetWriter(NativeJsonKeysetWriter.withOutputStream(os)) {

    actual constructor() : this(ByteArrayOutputStream())

    override fun write(): ByteArray = os.toByteArray()
}
