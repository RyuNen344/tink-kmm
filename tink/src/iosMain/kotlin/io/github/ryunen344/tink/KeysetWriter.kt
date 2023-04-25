package io.github.ryunen344.tink

import platform.Foundation.NSData

actual abstract class KeysetWriter {
    var data: NSData? = null
    actual abstract fun write(): ByteArray
}
