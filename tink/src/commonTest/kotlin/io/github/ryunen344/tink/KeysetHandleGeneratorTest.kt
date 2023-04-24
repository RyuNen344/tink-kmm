package io.github.ryunen344.tink

import io.github.ryunen344.tink.exception.GeneralSecurityException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class KeysetHandleGeneratorTest {
    @Test
    fun test_generateNew_when_config_not_registered_then_throws_error() {
        KeyTemplateSet.values().forEach {
            assertFailsWith<GeneralSecurityException> {
                KeysetHandleGenerator.generateNew(it.template())
            }
        }
    }
}
