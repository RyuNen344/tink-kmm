package io.github.ryunen344.tink

import com.google.crypto.tink.TINKBinaryKeysetReader
import io.github.ryunen344.tink.exception.IOException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import io.github.ryunen344.tink.util.toNSData
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr

@OptIn(ExperimentalForeignApi::class)
actual class BinaryKeysetReader actual constructor(bytes: ByteArray) :
    KeysetReader(
        native = memScopedInstance(
            block = { TINKBinaryKeysetReader(serializedKeyset = bytes.toNSData(), error = it.ptr) },
            onError = { throw IOException(cause = it.asThrowable()) }
        )
    )
