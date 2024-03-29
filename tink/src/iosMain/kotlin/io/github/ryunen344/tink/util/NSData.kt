package io.github.ryunen344.tink.util

import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.posix.memcpy

internal inline fun ByteArray.toNSData(): NSData = usePinned {
    NSData.dataWithBytes(
        bytes = it.startAddressOf,
        length = size.convert()
    )
}

internal inline fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned {
        memcpy(it.startAddressOf, bytes, length)
    }
}
