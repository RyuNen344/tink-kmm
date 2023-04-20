package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.template
import kotlin.test.Test
import kotlin.test.assertEquals

class DeterministicAeadTest {
    @Test
    fun test_exec_encryption() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
            .getPrimitive(DeterministicAead::class)
        val plaintext = "hogehogehowgheowa"
        val associatedData = "associated"
        println("input $plaintext")
        val encrypted =
            handle.encryptDeterministically(plaintext.encodeToByteArray(), associatedData.encodeToByteArray())
        println("encrypted $encrypted")
        val decrypted = handle.decryptDeterministically(encrypted, associatedData.encodeToByteArray())
        println("decrypted $decrypted")
        assertEquals(plaintext, decrypted.decodeToString())
    }
}
