package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.template
import kotlin.test.Test
import kotlin.test.assertEquals

class AeadTest {
    @Test
    fun test_exec_encryption() {
        AeadConfig().register()
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_GCM.template()).getPrimitive(Aead::class)
        val plaintext = "hogehogehowgheowa"
        val associatedData = "associated"
        println("input $plaintext")
        val encrypted = handle.encrypt(plaintext.encodeToByteArray(), associatedData.encodeToByteArray())
        println("encrypted $encrypted")
        val decrypted = handle.decrypt(encrypted, associatedData.encodeToByteArray())
        println("decrypted $decrypted")
        assertEquals(plaintext, decrypted.decodeToString())
    }
}
