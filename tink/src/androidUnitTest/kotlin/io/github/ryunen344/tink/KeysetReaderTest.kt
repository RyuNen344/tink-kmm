package io.github.ryunen344.tink

import io.github.ryunen344.tink.aead.AeadConfig
import io.github.ryunen344.tink.aead.register
import io.github.ryunen344.tink.signature.SignatureConfig
import io.github.ryunen344.tink.signature.register
import kotlin.test.Test

class KeysetReaderTest {
    @Test
    fun test_read() {
        runCatching {
            AeadConfig.register()
            val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_GCM.template())
            val writer = JsonKeysetWriter()
            handle.writeCleartext(writer)
            println(writer.write().decodeToString())
        }.onFailure {
            it.printStackTrace()
        }
    }

    @Test
    fun test_signature() {
        SignatureConfig.register()

        val privateHandle = KeysetHandleGenerator.generateNew(KeyTemplateSet.ECDSA_P256.template())
        val publicHandle = privateHandle.publicKeysetHandle()

        JsonKeysetWriter().run {
            privateHandle.writeCleartext(this)
            println(write().decodeToString())
        }

        JsonKeysetWriter().run {
            publicHandle.writeNoSecret(this)
            println(write().decodeToString())
        }
    }
}
