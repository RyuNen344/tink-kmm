package io.github.ryunen344.tink.daead

import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.JsonKeysetReader
import io.github.ryunen344.tink.getPrimitive
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class AndroidDeterministicAeadTest {

    @BeforeTest
    fun setup() {
        DeterministicAeadConfig.register()
    }

    @Test
    fun test_encrypt_given_json_keyset_then_success() {
        val handle = CleartextKeysetHandle.fromKeyset(JsonKeysetReader.withString(JSON_DAEAD_KEYSET).read())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext: ByteArray = "plaintext".encodeToByteArray()
        val associatedData: ByteArray = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)
        val decrypted = daead.decryptDeterministically(ciphertext, associatedData)
        assertContentEquals(plaintext, decrypted)
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
    }
}
