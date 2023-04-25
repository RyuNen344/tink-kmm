package io.github.ryunen344.tink

import io.github.ryunen344.tink.aead.AeadConfig
import io.github.ryunen344.tink.aead.register
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
}
