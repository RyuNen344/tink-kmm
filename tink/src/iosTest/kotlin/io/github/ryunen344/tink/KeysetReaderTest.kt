package io.github.ryunen344.tink

import com.google.crypto.tink.TINKKeysetReader
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.aead.AeadConfig
import kotlin.test.Test

class KeysetReaderTest {
    @Test
    fun test_read() {
        runCatching {
            AeadConfig().register()
            val aead = KeysetHandleGenerator
                .generateNew(KeyTemplateSet.AES256_GCM.template())
                .getPrimitive(Aead::class)
            KeysetHandleGenerator.read(TINKKeysetReader(), aead)
        }.onFailure {
            it.printStackTrace()
        }
    }
}
