package io.github.ryunen344.tink.aead

import io.github.ryunen344.tink.JsonKeysetReader
import io.github.ryunen344.tink.KeyTemplate
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.daead.register
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.readClearText
import io.github.ryunen344.tink.template
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class AeadTest {

    @BeforeTest
    fun setup() {
        AeadConfig.register()
    }

    private fun encrypt(
        template: KeyTemplate,
        plaintext: ByteArray = "plaintext".encodeToByteArray(),
        associatedData: ByteArray = "associatedData".encodeToByteArray(),
    ) {
        val handle = KeysetHandleGenerator.generateNew(template)
        val aead = handle.getPrimitive(Aead::class)

        val ciphertext = aead.encrypt(plaintext, associatedData)
        val decrypted = aead.decrypt(ciphertext, associatedData)

        assertContentEquals(plaintext, decrypted)
    }

    private fun decryptInvalidAssociateData(
        template: KeyTemplate,
    ) {
        val handle = KeysetHandleGenerator.generateNew(template)
        val aead = handle.getPrimitive(Aead::class)
        val associatedData = "associatedData".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            aead.decrypt(invalid, associatedData)
        }
    }

    private fun decryptInvalidData(
        template: KeyTemplate,
    ) {
        val handle = KeysetHandleGenerator.generateNew(template)
        val aead = handle.getPrimitive(Aead::class)
        val associatedData = "associatedData".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            aead.decrypt(invalid, associatedData)
        }
    }

    @Test
    fun test_encrypt_AES128_GCM_then_success() = encrypt(KeyTemplateSet.AES128_GCM.template())

    @Test
    fun test_encrypt_AES128_GCM_given_empty_then_success() =
        encrypt(KeyTemplateSet.AES128_GCM.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES128_GCM_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES128_GCM.template())

    @Test
    fun test_encrypt_AES128_GCM_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES128_GCM.template())

    @Test
    fun test_encrypt_AES128_GCM_RAW_then_success() = encrypt(KeyTemplateSet.AES128_GCM_RAW.template())

    @Test
    fun test_encrypt_AES128_GCM_RAW_given_empty_then_success() =
        encrypt(KeyTemplateSet.AES128_GCM_RAW.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES128_GCM_RAW_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES128_GCM_RAW.template())

    @Test
    fun test_encrypt_AES128_GCM_RAW_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES128_GCM_RAW.template())

    @Test
    fun test_encrypt_AES256_GCM_then_success() = encrypt(KeyTemplateSet.AES256_GCM.template())

    @Test
    fun test_encrypt_AES256_GCM_given_empty_then_success() =
        encrypt(KeyTemplateSet.AES256_GCM.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES256_GCM_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES256_GCM.template())

    @Test
    fun test_encrypt_AES256_GCM_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES256_GCM.template())

    @Test
    fun test_encrypt_AES256_GCM_RAW_then_success() = encrypt(KeyTemplateSet.AES256_GCM_RAW.template())

    @Test
    fun test_encrypt_AES256_GCM_RAW_given_empty_then_success() =
        encrypt(KeyTemplateSet.AES256_GCM_RAW.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES256_GCM_RAW_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES256_GCM_RAW.template())

    @Test
    fun test_encrypt_AES256_GCM_RAW_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES256_GCM_RAW.template())

    @Test
    fun test_encrypt_AES128_CTR_HMAC_SHA256_then_success() = encrypt(KeyTemplateSet.AES128_CTR_HMAC_SHA256.template())

    @Test
    fun test_encrypt_AES128_CTR_HMAC_SHA256_given_empty_then_success() =
        encrypt(KeyTemplateSet.AES128_CTR_HMAC_SHA256.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES128_CTR_HMAC_SHA256_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES128_CTR_HMAC_SHA256.template())

    @Test
    fun test_encrypt_AES128_CTR_HMAC_SHA256_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES128_CTR_HMAC_SHA256.template())

    @Test
    fun test_encrypt_AES256_CTR_HMAC_SHA256_then_success() = encrypt(KeyTemplateSet.AES256_CTR_HMAC_SHA256.template())

    @Test
    fun test_encrypt_AES256_CTR_HMAC_SHA256_given_empty_then_success() =
        encrypt(KeyTemplateSet.AES256_CTR_HMAC_SHA256.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES256_CTR_HMAC_SHA256_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES256_CTR_HMAC_SHA256.template())

    @Test
    fun test_encrypt_AES256_CTR_HMAC_SHA256_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES256_CTR_HMAC_SHA256.template())

    @Test
    fun test_encrypt_AES128_EAX_then_success() = encrypt(KeyTemplateSet.AES128_EAX.template())

    @Test
    fun test_encrypt_AES128_EAX_then_given_empty_success() =
        encrypt(KeyTemplateSet.AES128_EAX.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES128_EAX_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES128_EAX.template())

    @Test
    fun test_encrypt_AES128_EAX_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES128_EAX.template())

    @Test
    fun test_encrypt_AES256_EAX_then_success() = encrypt(KeyTemplateSet.AES256_EAX.template())

    @Test
    fun test_encrypt_AES256_EAX_then_given_empty_success() =
        encrypt(KeyTemplateSet.AES256_EAX.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_AES256_EAX_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.AES256_EAX.template())

    @Test
    fun test_encrypt_AES256_EAX_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.AES256_EAX.template())

    @Test
    fun test_encrypt_XCHACHA20_POLY1305_then_success() = encrypt(KeyTemplateSet.XCHACHA20_POLY1305.template())

    @Test
    fun test_encrypt_XCHACHA20_POLY1305_given_empty_then_success() =
        encrypt(KeyTemplateSet.XCHACHA20_POLY1305.template(), "".encodeToByteArray())

    @Test
    fun test_encrypt_XCHACHA20_POLY1305_given_invalid_associate_data_then_throw_error() =
        decryptInvalidAssociateData(KeyTemplateSet.XCHACHA20_POLY1305.template())

    @Test
    fun test_encrypt_XCHACHA20_POLY1305_given_invalid_data_then_throw_error() =
        decryptInvalidData(KeyTemplateSet.XCHACHA20_POLY1305.template())

    @Test
    fun test_encrypt_given_json_keyset_then_success() {
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_AEAD_KEYSET))
        val aead = handle.getPrimitive(Aead::class)
        val plaintext: ByteArray = "plaintext".encodeToByteArray()
        val associatedData: ByteArray = "associatedData".encodeToByteArray()
        val ciphertext = aead.encrypt(plaintext, associatedData)
        val decrypted = aead.decrypt(ciphertext, associatedData)
        assertContentEquals(plaintext, decrypted)
    }

    @Test
    fun test_encrypt_given_multiple_keyset_then_success() {
        val plaintext: ByteArray = "plaintext".encodeToByteArray()
        val associatedData: ByteArray = "associatedData".encodeToByteArray()

        val primaryHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_AEAD_KEYSET_WITH_MULTIPLE_KEYS))
        val primaryAead = primaryHandle.getPrimitive(Aead::class)
        assertContentEquals(
            plaintext,
            primaryAead.decrypt(
                primaryAead.encrypt(plaintext, associatedData),
                associatedData
            )
        )

        // Also test that aead can decrypt ciphertexts encrypted with a non-primary key. We use
        // JSON_AEAD_KEYSET to encrypt with the first key.
        val subHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_AEAD_KEYSET))
        val subAead = subHandle.getPrimitive(Aead::class)
        assertContentEquals(
            plaintext,
            subAead.decrypt(
                subAead.encrypt(plaintext, associatedData),
                associatedData
            )
        )
    }

    @Test
    fun test_getPrimitive_given_NonAeadKeyset_then_throws_error() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        handle.getPrimitive(DeterministicAead::class)

        assertFailsWith<GeneralSecurityException> {
            handle.getPrimitive(Aead::class)
        }
    }

    private companion object {
        val JSON_AEAD_KEYSET = """
            {
                "primaryKeyId": 42818733,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesGcmKey",
                            "keyMaterialType": "SYMMETRIC",
                            "value": "GhCC74uJ+2f4qlpaHwR4ylNQ"
                        },
                        "outputPrefixType": "TINK",
                        "keyId": 42818733,
                        "status": "ENABLED"
                    }
                ]
            }
        """.trimIndent()

        val JSON_AEAD_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 365202604,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesGcmKey",
                            "keyMaterialType": "SYMMETRIC",
                            "value": "GhCC74uJ+2f4qlpaHwR4ylNQ"
                        },
                        "outputPrefixType": "TINK",
                        "keyId": 42818733,
                        "status": "ENABLED"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesEaxKey",
                            "keyMaterialType": "SYMMETRIC",
                            "value": "EgIIEBogU4nieBfIeJHBrhC+TjezFgxkkuhQHbyWkUMH+7atLxI="
                        },
                        "outputPrefixType": "RAW",
                        "keyId": 365202604,
                        "status": "ENABLED"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey",
                            "keyMaterialType": "SYMMETRIC",
                            "value": "GigaIMttlipP/JvQOpIB0NYhDPoLgWBiIxmtaWbSPa2TeQOmEgQQEAgDEhYaEPcCMmPLgRGhmMmSC4AJ1CESAggQ"
                        },
                        "outputPrefixType": "LEGACY",
                        "keyId": 277095770,
                        "status": "ENABLED"
                    }
                ]
            }
        """.trimIndent()

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
