package io.github.ryunen344.tink

internal typealias NativeKeysetReader = com.google.crypto.tink.KeysetReader

actual open class KeysetReader(private val native: NativeKeysetReader) : NativeKeysetReader by native
