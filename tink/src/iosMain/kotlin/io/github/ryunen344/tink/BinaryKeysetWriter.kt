package io.github.ryunen344.tink

import io.github.ryunen344.tink.util.toByteArray

actual class BinaryKeysetWriter : KeysetWriter() {
    override fun write(): ByteArray = value?.toByteArray() ?: ByteArray(0)
}
