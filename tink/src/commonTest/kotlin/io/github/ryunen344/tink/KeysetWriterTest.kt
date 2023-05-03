package io.github.ryunen344.tink

import io.github.ryunen344.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.daead.register
import kotlin.test.Test
import kotlin.test.assertEquals

class KeysetWriterTest {
    @Test
    fun test_read() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        val writer = BinaryKeysetWriter()
        handle.writeCleartext(writer)
        assertEquals(
            DAEAD_KEYSET_CONTENT,
            writer.write().contentToString()
        )
    }

    private companion object {
        val JSON_DAEAD_KEYSET = """
            {
                "primaryKeyId": 961932622,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesSivKey",
                            "keyMaterialType": "SYMMETRIC",
                            "value": "EkCJ9r5iwc5uxq5ugFyrHXh5dijTa7qalWUgZ8Gf08RxNd545FjtLMYL7ObcaFtCSkvV2+7u6F2DN+kqUjAfkf2W"
                        },
                        "outputPrefixType": "TINK",
                        "keyId": 961932622,
                        "status": "ENABLED"
                    }
                ]
            }
        """.trimIndent()

        const val DAEAD_KEYSET_CONTENT =
            "[8, -50, -38, -41, -54, 3, 18, -124, 1, 10, 120, 10, 48, 116, 121, 112, 101, 46, 103, 111, 111, 103, 108, 101, 97, 112, 105, 115, 46, 99, 111, 109, 47, 103, 111, 111, 103, 108, 101, 46, 99, 114, 121, 112, 116, 111, 46, 116, 105, 110, 107, 46, 65, 101, 115, 83, 105, 118, 75, 101, 121, 18, 66, 18, 64, -119, -10, -66, 98, -63, -50, 110, -58, -82, 110, -128, 92, -85, 29, 120, 121, 118, 40, -45, 107, -70, -102, -107, 101, 32, 103, -63, -97, -45, -60, 113, 53, -34, 120, -28, 88, -19, 44, -58, 11, -20, -26, -36, 104, 91, 66, 74, 75, -43, -37, -18, -18, -24, 93, -125, 55, -23, 42, 82, 48, 31, -111, -3, -106, 24, 1, 16, 1, 24, -50, -38, -41, -54, 3, 32, 1]"
    }
}
