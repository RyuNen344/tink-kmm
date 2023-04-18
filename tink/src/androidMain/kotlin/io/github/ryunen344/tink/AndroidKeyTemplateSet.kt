package io.github.ryunen344.tink

import com.google.crypto.tink.KeyTemplates

actual val KeyTemplateSet.template: KeyTemplate
    get() = KeyTemplates.get(value)
