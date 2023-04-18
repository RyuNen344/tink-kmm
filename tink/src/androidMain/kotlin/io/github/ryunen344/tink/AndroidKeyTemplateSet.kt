package io.github.ryunen344.tink

import com.google.crypto.tink.KeyTemplates
import io.github.ryunen344.tink.exception.GeneralSecurityException

@Throws(GeneralSecurityException::class)
actual fun KeyTemplateSet.template(): KeyTemplate = KeyTemplates.get(value)
