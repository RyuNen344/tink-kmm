package io.github.ryunen344.tink

import io.github.ryunen344.tink.aead.AeadConfig
import io.github.ryunen344.tink.aead.register
import kotlin.test.Test

class KeysetWriterTest {
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

// {"primaryKeyId":1767300617,"key":[{"keyData":{"typeUrl":"type.googleapis.com/google.crypto.tink.AesGcmKey","value":"GiAJ8tStZdr5C1KQ7EJUOO57IkyKuFUXLvSJBTcQTdhgkw==","keyMaterialType":"SYMMETRIC"},"status":"ENABLED","keyId":1767300617,"outputPrefixType":"TINK"}]}

// content [8, -70, -59, -34, -40, 12, 18, 100, 10, 88, 10, 48, 116, 121, 112, 101, 46, 103, 111, 111, 103, 108, 101, 97, 112, 105, 115, 46, 99, 111, 109, 47, 103, 111, 111, 103, 108, 101, 46, 99, 114, 121, 112, 116, 111, 46, 116, 105, 110, 107, 46, 65, 101, 115, 71, 99, 109, 75, 101, 121, 18, 34, 26, 32, 110, -6, 90, 23, -77, 39, 59, 107, -36, -94, 14, -51, 64, 20, -117, 48, -23, 121, 97, 100, 105, 2, -127, 98, 17, 104, 117, -88, 44, -53, 16, -119, 24, 1, 16, 1, 24, -70, -59, -34, -40, 12, 32, 1]
// nsstring null
// ����d
// X
// 0type.googleapis.com/google.crypto.tink.AesGcmKey" n�Z�';kܢ�@�0�yadi�bhu�,������
