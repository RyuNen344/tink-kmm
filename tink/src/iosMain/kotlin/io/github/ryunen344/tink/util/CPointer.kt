package io.github.ryunen344.tink.util

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.Pinned
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.pin

@OptIn(ExperimentalForeignApi::class)
private val emptyAddressByte = ByteArray(1).pin().addressOf(0)

@OptIn(ExperimentalForeignApi::class)
internal val Pinned<ByteArray>.startAddressOf: CPointer<ByteVar>
    get() = if (get().isNotEmpty()) addressOf(0) else emptyAddressByte
