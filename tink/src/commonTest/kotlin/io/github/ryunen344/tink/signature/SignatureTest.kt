package io.github.ryunen344.tink.signature

import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.publicKeysetHandle
import io.github.ryunen344.tink.template
import kotlin.test.Test

class SignatureTest {
    @Test
    fun test_exec_encryption() {
        SignatureConfig.register()
        val privateKeysetHandle = KeysetHandleGenerator.generateNew(KeyTemplateSet.ED25519.template())
        val signer = privateKeysetHandle.getPrimitive(PublicKeySign::class)

        val input = "hogewefaewawefawefa"
        val signature = signer.sign(input.encodeToByteArray())

        println("signature ${signature.decodeToString()}")

        val publicKeysetHandle = privateKeysetHandle.publicKeysetHandle()
        val verifier = publicKeysetHandle.getPrimitive(PublicKeyVerify::class)
        verifier.verify(signature, input.encodeToByteArray())
    }
}
