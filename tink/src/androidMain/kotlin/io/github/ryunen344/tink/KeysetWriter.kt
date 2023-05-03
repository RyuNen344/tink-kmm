package io.github.ryunen344.tink

internal typealias NativeKeysetWriter = com.google.crypto.tink.KeysetWriter

actual abstract class KeysetWriter(private val native: NativeKeysetWriter) : NativeKeysetWriter by native {
    actual abstract fun write(): ByteArray
}
