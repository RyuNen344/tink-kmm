package io.github.ryunen344.tink

typealias NativeBinaryKeysetReader = com.google.crypto.tink.BinaryKeysetReader

actual class BinaryKeysetReader actual constructor(bytes: ByteArray) :
    KeysetReader(NativeBinaryKeysetReader.withBytes(bytes))
