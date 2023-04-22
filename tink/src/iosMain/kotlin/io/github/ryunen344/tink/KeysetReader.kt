package io.github.ryunen344.tink

import com.google.crypto.tink.TINKKeysetReader

actual open class KeysetReader(val native: TINKKeysetReader)
