package io.github.ryunen344.tink.hybrid

import io.github.ryunen344.tink.JsonKeysetReader
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.daead.register
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.publicKeysetHandle
import io.github.ryunen344.tink.readClearText
import io.github.ryunen344.tink.template
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class HybridTest {

    private fun verify(
        set: KeyTemplateSet,
        plaintext: ByteArray = "plaintext".encodeToByteArray(),
        contextInfo: ByteArray = "contextInfo".encodeToByteArray(),
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val publicHandle = privateHandle.publicKeysetHandle()
        val encryptor = publicHandle.getPrimitive(HybridEncrypt::class)
        val decryptor = privateHandle.getPrimitive(HybridDecrypt::class)

        val cipherText = encryptor.encrypt(plaintext, contextInfo)
        assertContentEquals(
            plaintext,
            decryptor.decrypt(cipherText, contextInfo)
        )
    }

    private fun invalidData(
        set: KeyTemplateSet,
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val decryptor = privateHandle.getPrimitive(HybridDecrypt::class)

        val contextInfo = "contextInfo".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            decryptor.decrypt(invalid, contextInfo)
        }
    }

    private fun invalidContextInfo(
        set: KeyTemplateSet,
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val publicHandle = privateHandle.publicKeysetHandle()
        val encryptor = publicHandle.getPrimitive(HybridEncrypt::class)
        val decryptor = privateHandle.getPrimitive(HybridDecrypt::class)

        val plaintext = "plaintext".encodeToByteArray()
        val contextInfo = "contextInfo".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()
        val cipherText = encryptor.encrypt(plaintext, contextInfo)

        assertFailsWith<GeneralSecurityException> {
            decryptor.decrypt(cipherText, invalid)
        }
    }

    private fun match(
        set: KeyTemplateSet,
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val publicHandle = privateHandle.publicKeysetHandle()
        val encryptor = publicHandle.getPrimitive(HybridEncrypt::class)

        val plaintext = "plaintext".encodeToByteArray()
        val contextInfo = "contextInfo".encodeToByteArray()
        val cipherText = encryptor.encrypt(plaintext, contextInfo)

        val otherHandle = KeysetHandleGenerator.generateNew(set.template())
        val otherDecrypter = otherHandle.getPrimitive(HybridDecrypt::class)

        assertFailsWith<GeneralSecurityException> {
            otherDecrypter.decrypt(cipherText, contextInfo)
        }
    }

    @BeforeTest
    fun setup() {
        HybridConfig.register()
    }

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_then_success() =
        verify(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM)

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM, plaintext = "".encodeToByteArray())

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_given_empty_context_info_then_success() =
        verify(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM, contextInfo = "".encodeToByteArray())

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM)

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_given_invalid_context_then_throw_error() =
        invalidContextInfo(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM)

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM)

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_then_success() =
        verify(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256)

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256, plaintext = "".encodeToByteArray())

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_given_empty_context_info_then_success() =
        verify(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256, contextInfo = "".encodeToByteArray())

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256)

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_given_invalid_context_then_throw_error() =
        invalidContextInfo(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256)

    @Test
    fun test_encrypt_ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256)

    @Test
    fun test_encrypt_given_json_keyset_then_success() {
        val privateHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PRIVATE_KEYSET))
        val publicHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PUBLIC_KEYSET))

        val encryptor = publicHandle.getPrimitive(HybridEncrypt::class)
        val decryptor = privateHandle.getPrimitive(HybridDecrypt::class)

        val plaintext = "plaintext".encodeToByteArray()
        val contextInfo = "contextInfo".encodeToByteArray()
        val ciphertext = encryptor.encrypt(plaintext, contextInfo)
        val decrypted = decryptor.decrypt(ciphertext, contextInfo)
        assertContentEquals(plaintext, decrypted)
    }

    @Test
    fun test_encrypt_given_multiple_json_keyset_then_success() {
        val privateHandle =
            KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PRIVATE_KEYSET_WITH_MULTIPLE_KEYS))
        val publicHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PUBLIC_KEYSET_WITH_MULTIPLE_KEYS))

        val encryptor = publicHandle.getPrimitive(HybridEncrypt::class)
        val decryptor = privateHandle.getPrimitive(HybridDecrypt::class)

        val plaintext = "plaintext".encodeToByteArray()
        val contextInfo = "contextInfo".encodeToByteArray()
        val ciphertext = encryptor.encrypt(plaintext, contextInfo)
        val decrypted = decryptor.decrypt(ciphertext, contextInfo)
        assertContentEquals(plaintext, decrypted)

        val otherPublicHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PUBLIC_KEYSET))
        val otherEncryptor = otherPublicHandle.getPrimitive(HybridEncrypt::class)
        val otherCiphertext = otherEncryptor.encrypt(plaintext, contextInfo)
        assertContentEquals(
            plaintext,
            decryptor.decrypt(otherCiphertext, contextInfo)
        )
    }

    @Test
    fun test_getPrimitive_given_NonSignatureKeyset_then_throws_error() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        handle.getPrimitive(DeterministicAead::class)

        assertFailsWith<GeneralSecurityException> {
            handle.getPrimitive(HybridEncrypt::class)
        }
        assertFailsWith<GeneralSecurityException> {
            handle.getPrimitive(HybridDecrypt::class)
        }
    }

    private companion object {
        val JSON_PRIVATE_KEYSET = """
            {
                "primaryKeyId": 1885000158,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HpkePrivateKey",
                            "value": "GiBXM1jmpJqe7HUTTkQxRwEld3bvIPTBhqGcI09ki9H0mRIqGiCwWh0y63GfObeWuYZcuLIiFz+15ElOFL7rhf9rbWxdBBIGGAEQAQgB",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 1885000158,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
        """.trimIndent()

        val JSON_PUBLIC_KEYSET = """
            {
                "primaryKeyId": 1885000158,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HpkePublicKey",
                            "value": "GiCwWh0y63GfObeWuYZcuLIiFz+15ElOFL7rhf9rbWxdBBIGGAEQAQgB",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 1885000158,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
            """.trimIndent()

        val JSON_PRIVATE_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 405658073,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HpkePrivateKey",
                            "value": "GiBXM1jmpJqe7HUTTkQxRwEld3bvIPTBhqGcI09ki9H0mRIqGiCwWh0y63GfObeWuYZcuLIiFz+15ElOFL7rhf9rbWxdBBIGGAEQAQgB",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 1885000158,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey",
                            "value": "GiAGLU3EgraobyU/aOJalcfR2jUUwK/ubd5mTYHIzLHBnBKiASIgJDF8fcNyDS6BcgYpeVPkJ2/ZBG+Mum30OId4D4CzDuQaIP9J2qo487Shr+MxMIkE3VvMro1r4Z+VFoTP3QWVTpziElwYARJSElAYARISEggQIAoEEBAIAwoGEBAKAggQCjh0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNDdHJIbWFjQWVhZEtleQoEEAMIAg==",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 405658073,
                        "outputPrefixType": "RAW"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HpkePrivateKey",
                            "value": "GiAnd0VLE8exo149gJ49nkifg03YQLNnRMKfna0AdfYjnBIqGiABOUjRp8FQgppUbZlHCkxRgxGc3jYiChCkm+pf9BL3YhIGGAIQAQgB",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 2085058073,
                        "outputPrefixType": "LEGACY"
                    }
                ]
            }
        """.trimIndent()

        // Keyset with the public keys of the keys from JSON_PRIVATE_KEYSET_WITH_MULTIPLE_KEYS.
        val JSON_PUBLIC_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 405658073,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HpkePublicKey",
                            "value": "GiCwWh0y63GfObeWuYZcuLIiFz+15ElOFL7rhf9rbWxdBBIGGAEQAQgB",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 1885000158,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey",
                            "value": "IiAkMXx9w3INLoFyBil5U+Qnb9kEb4y6bfQ4h3gPgLMO5Bog/0naqjjztKGv4zEwiQTdW8yujWvhn5UWhM/dBZVOnOISXBgBElISUBgBEhISCBAgCgQQEAgDCgYQEAoCCBAKOHR5cGUuZ29vZ2xlYXBpcy5jb20vZ29vZ2xlLmNyeXB0by50aW5rLkFlc0N0ckhtYWNBZWFkS2V5CgQQAwgC",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 405658073,
                        "outputPrefixType": "RAW"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HpkePublicKey",
                            "value": "GiABOUjRp8FQgppUbZlHCkxRgxGc3jYiChCkm+pf9BL3YhIGGAIQAQgB",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 2085058073,
                        "outputPrefixType": "LEGACY"
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
