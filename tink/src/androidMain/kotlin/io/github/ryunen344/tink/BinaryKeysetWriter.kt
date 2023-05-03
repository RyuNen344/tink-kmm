package io.github.ryunen344.tink

import java.io.ByteArrayOutputStream

typealias NativeBinaryKeysetWriter = com.google.crypto.tink.BinaryKeysetWriter

actual class BinaryKeysetWriter(
    private val os: ByteArrayOutputStream = ByteArrayOutputStream(),
) : KeysetWriter(NativeBinaryKeysetWriter.withOutputStream(os)) {

    actual constructor() : this(ByteArrayOutputStream())

    override fun write(): ByteArray = os.toByteArray()
}
