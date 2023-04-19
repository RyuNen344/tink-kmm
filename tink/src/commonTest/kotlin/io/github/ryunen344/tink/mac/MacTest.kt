package io.github.ryunen344.tink.mac

import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.template
import kotlin.test.Test

class MacTest {
    @Test
    fun test_exec_encryption() {
        MacConfig().register()
        val mac =
            KeysetHandleGenerator.generateNew(KeyTemplateSet.HMAC_SHA512_512BITTAG.template()).getPrimitive(Mac::class)
        val plaintext = "hogehogehowgheowa"
        val tag = mac.computeMac(plaintext.encodeToByteArray())
        mac.verifyMac(tag, plaintext.encodeToByteArray())
    }
}
