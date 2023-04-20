package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.publicKeysetHandle
import io.github.ryunen344.tink.template
import kotlin.test.Test
import kotlin.test.assertEquals

class HybridTest {
    @Test
    fun test_exec_encryption() {
        runCatching {
            HybridConfig.register()
            val privateKeysetHandle =
                KeysetHandleGenerator.generateNew(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM.template())
            val publicKeysetHandle = privateKeysetHandle.publicKeysetHandle()

            val plaintext = "hogehogehowgheowa"
            val associatedData = "associated"
            println("input $plaintext")
            val encrypted =
                publicKeysetHandle.getPrimitive(HybridEncrypt::class)
                    .encrypt(plaintext.encodeToByteArray(), associatedData.encodeToByteArray())
            println("encrypted $encrypted")
            val decrypted =
                privateKeysetHandle.getPrimitive(HybridDecrypt::class)
                    .decrypt(encrypted, associatedData.encodeToByteArray())
            println("decrypted $decrypted")
            assertEquals(plaintext, decrypted.decodeToString())
        }.onFailure {
            it.printStackTrace()
        }
    }
}
