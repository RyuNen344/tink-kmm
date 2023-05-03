package io.github.ryunen344.tink

import io.github.ryunen344.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.daead.register
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.signature.SignatureConfig
import io.github.ryunen344.tink.signature.register
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class KeysetWriterTest {
    @Test
    fun test_write() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        val writer = BinaryKeysetWriter()
        handle.writeCleartext(writer)
        assertEquals(
            DAEAD_KEYSET_CONTENT,
            writer.write().contentToString()
        )
    }

    @Test
    fun test_write_public_handle() {
        SignatureConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PUBLIC_KEYSET))
        val writer = BinaryKeysetWriter()
        handle.writeNoSecret(writer)
        assertEquals(
            PUBLIC_KEYSET_CONTENT,
            writer.write().contentToString()
        )
    }

    @Test
    fun test_write_private_handle_then_throw_error() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        val writer = BinaryKeysetWriter()

        assertFailsWith<GeneralSecurityException> {
            handle.writeNoSecret(writer)
        }
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

        @Suppress("MaxLineLength")
        const val DAEAD_KEYSET_CONTENT =
            "[8, -50, -38, -41, -54, 3, 18, -124, 1, 10, 120, 10, 48, 116, 121, 112, 101, 46, 103, 111, 111, 103, 108, 101, 97, 112, 105, 115, 46, 99, 111, 109, 47, 103, 111, 111, 103, 108, 101, 46, 99, 114, 121, 112, 116, 111, 46, 116, 105, 110, 107, 46, 65, 101, 115, 83, 105, 118, 75, 101, 121, 18, 66, 18, 64, -119, -10, -66, 98, -63, -50, 110, -58, -82, 110, -128, 92, -85, 29, 120, 121, 118, 40, -45, 107, -70, -102, -107, 101, 32, 103, -63, -97, -45, -60, 113, 53, -34, 120, -28, 88, -19, 44, -58, 11, -20, -26, -36, 104, 91, 66, 74, 75, -43, -37, -18, -18, -24, 93, -125, 55, -23, 42, 82, 48, 31, -111, -3, -106, 24, 1, 16, 1, 24, -50, -38, -41, -54, 3, 32, 1]"

        val JSON_PUBLIC_KEYSET = """
            {
                "primaryKeyId": 775870498,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EcdsaPublicKey",
                            "value": "IiApA+NmYivxRfhMuvTKZAwqETmn+WagBP/reucEjEvXkRog1AJ5GBzf+n27xnj9KcoGllF9NIFfQrDEP99FNH+Cne4SBhgCEAIIAw==",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 775870498,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
        """.trimIndent()

        @Suppress("MaxLineLength")
        const val PUBLIC_KEYSET_CONTENT =
            "[8, -94, -80, -5, -15, 2, 18, -108, 1, 10, -121, 1, 10, 53, 116, 121, 112, 101, 46, 103, 111, 111, 103, 108, 101, 97, 112, 105, 115, 46, 99, 111, 109, 47, 103, 111, 111, 103, 108, 101, 46, 99, 114, 121, 112, 116, 111, 46, 116, 105, 110, 107, 46, 69, 99, 100, 115, 97, 80, 117, 98, 108, 105, 99, 75, 101, 121, 18, 76, 34, 32, 41, 3, -29, 102, 98, 43, -15, 69, -8, 76, -70, -12, -54, 100, 12, 42, 17, 57, -89, -7, 102, -96, 4, -1, -21, 122, -25, 4, -116, 75, -41, -111, 26, 32, -44, 2, 121, 24, 28, -33, -6, 125, -69, -58, 120, -3, 41, -54, 6, -106, 81, 125, 52, -127, 95, 66, -80, -60, 63, -33, 69, 52, 127, -126, -99, -18, 18, 6, 24, 2, 16, 2, 8, 3, 24, 3, 16, 1, 24, -94, -80, -5, -15, 2, 32, 1]"
    }
}
