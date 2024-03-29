package io.github.ryunen344.tink

import io.github.ryunen344.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.daead.register
import io.github.ryunen344.tink.hybrid.HybridConfig
import io.github.ryunen344.tink.hybrid.register
import io.github.ryunen344.tink.signature.SignatureConfig
import io.github.ryunen344.tink.signature.register
import kotlin.test.Test

class KeyTemplateTest {
    @Test
    fun test_KeyTemplateSet() {
        HybridConfig.register()
        DeterministicAeadConfig.register()
        SignatureConfig.register()
        KeyTemplateSet.values().map(KeyTemplateSet::template).forEach(::println)
    }
}
