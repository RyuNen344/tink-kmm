package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.JsonKeysetReader
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.readClearText
import io.github.ryunen344.tink.template
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class DeterministicAeadTest {

    @BeforeTest
    fun setup() {
        DeterministicAeadConfig.register()
    }

    @Test
    fun test_encrypt_then_success() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)
        val decrypted = daead.decryptDeterministically(ciphertext, associatedData)

        assertContentEquals(plaintext, decrypted)
        assertContentEquals(ciphertext, daead.encryptDeterministically(plaintext, associatedData))
    }

    @Test
    fun test_encrypt_given_empty_then_success() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val empty = "".encodeToByteArray()

        assertContentEquals(
            empty,
            daead.decryptDeterministically(daead.encryptDeterministically(empty, associatedData), associatedData)
        )
        assertContentEquals(
            plaintext,
            daead.decryptDeterministically(daead.encryptDeterministically(plaintext, empty), empty)
        )
    }

    @Test
    fun test_decrypt_given_invalid_associate_data_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            daead.decryptDeterministically(ciphertext, invalid)
        }
    }

    @Test
    fun test_decrypt_given_invalid_data_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val associatedData = "associatedData".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            daead.decryptDeterministically(invalid, associatedData)
        }
    }

    @Test
    fun test_decrypt_given_empty_data_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val associatedData = "associatedData".encodeToByteArray()
        val empty = "".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            daead.decryptDeterministically(empty, associatedData)
        }
    }

    @Test
    fun test_decrypt_given_other_daead_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)

        val otherHandle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val otherDaead = otherHandle.getPrimitive(DeterministicAead::class)

        assertFailsWith<GeneralSecurityException> {
            otherDaead.decryptDeterministically(ciphertext, associatedData)
        }
    }

    @Test
    fun test_encrypt_given_json_keyset_then_success() {
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext: ByteArray = "plaintext".encodeToByteArray()
        val associatedData: ByteArray = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)
        val decrypted = daead.decryptDeterministically(ciphertext, associatedData)
        assertContentEquals(plaintext, decrypted)
    }

    @Test
    fun test_encrypt_given_multiple_keyset_then_success() {
        val plaintext: ByteArray = "plaintext".encodeToByteArray()
        val associatedData: ByteArray = "associatedData".encodeToByteArray()

        val primaryHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET_WITH_MULTIPLE_KEYS))
        val primaryDaead = primaryHandle.getPrimitive(DeterministicAead::class)
        assertContentEquals(
            plaintext,
            primaryDaead.decryptDeterministically(
                primaryDaead.encryptDeterministically(plaintext, associatedData),
                associatedData
            )
        )

        // Also test that daead can decrypt ciphertexts encrypted with a non-primary key. We use
        // JSON_DAEAD_KEYSET to encrypt with the first key.
        val secondaryHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        val secondaryDaead = secondaryHandle.getPrimitive(DeterministicAead::class)
        assertContentEquals(
            plaintext,
            secondaryDaead.decryptDeterministically(
                secondaryDaead.encryptDeterministically(plaintext, associatedData),
                associatedData
            )
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

        val JSON_DAEAD_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 385749617,
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
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesSivKey",
                            "value": "EkCGjyLCW8IOilSjFtkBOvpQoOA8ZsCAsFnCawU9ySiii3KefQkY4pGZcdlwJypOZem1/L+wPthYeCo4xmdq68hl",
                            "keyMaterialType": "SYMMETRIC"
                        },
                        "status": "ENABLED",
                        "keyId": 385749617,
                        "outputPrefixType": "RAW"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesSivKey",
                            "value": "EkCCo6EJBokVl3uTcZMA5iCtQArJliOlBBBfjmZ+IHdLGCatgWJ/tsUi2cmpw0o3yXyJaJbyT06kUCEP+GvFIjCQ",
                            "keyMaterialType": "SYMMETRIC"
                        },
                        "status": "ENABLED",
                        "keyId": 919668303,
                        "outputPrefixType": "LEGACY"
                    }
                ]
            }
        """.trimIndent()
    }
}
