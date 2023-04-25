package io.github.ryunen344.tink

expect abstract class KeysetWriter {
    abstract fun write(): ByteArray
}
