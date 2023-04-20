package io.github.ryunen344.tink

import com.google.crypto.tink.BinaryKeysetReader
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.AeadConfig
import io.github.ryunen344.tink.aead.register
import kotlin.test.Test

class KeysetReaderTest {
    @Test
    fun test_read() {
        runCatching {
            AeadConfig.register()
            val aead = KeysetHandleGenerator
                .generateNew(KeyTemplateSet.AES256_GCM.template())
                .getPrimitive(Aead::class)
            KeysetHandleGenerator.read(KeysetReader(BinaryKeysetReader.withBytes("hoge".encodeToByteArray())), aead)
        }.onFailure {
            it.printStackTrace()
        }
    }
}
