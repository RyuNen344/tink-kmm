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
                "primaryKeyId": 647048814,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey",
                            "value": "EosBEkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARogI4P/E3HzF6GSRNM4XlRwKBjGw81REj8ovlBno2uNvc8iIQC7Zjep7K4nPGJljgg6GCOrovBJcJRGWMsg8XLDTh0CdxogOIDYp690Aa0r2+xWsdhEZzRS5MVg8y0BdwQwMuYR63s=",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 647048814,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
        """.trimIndent()

        val JSON_PUBLIC_KEYSET = """
            {
                "primaryKeyId": 647048814,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey",
                            "value": "EkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARogI4P/E3HzF6GSRNM4XlRwKBjGw81REj8ovlBno2uNvc8iIQC7Zjep7K4nPGJljgg6GCOrovBJcJRGWMsg8XLDTh0Cdw==",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 647048814,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
        """.trimIndent()

        val JSON_PRIVATE_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 1013057693,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey",
                            "value": "EosBEkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARogI4P/E3HzF6GSRNM4XlRwKBjGw81REj8ovlBno2uNvc8iIQC7Zjep7K4nPGJljgg6GCOrovBJcJRGWMsg8XLDTh0CdxogOIDYp690Aa0r2+xWsdhEZzRS5MVg8y0BdwQwMuYR63s=",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 647048814,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey",
                            "value": "EooBEkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARogYDMh2pw+/IEZ5OTcWsnl3k8QunjsB1spu2Ex71L82WEiIECb/Un5ANDbIFdOpf+fxK0DJiTno1XVKuJym1WCqZTzGiBC8yu+DPjOz2Ut+oNkH73hxUcpgWmuJ+NPEqu5GbkLoQ==",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 418995680,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey",
                            "value": "EowBEkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARohAMJtGycPgL1lcApnYiP3MbURUE5tkkOxdeiOUTxsclmLIiEA4aNiPurRhAnYMdpLS52MbOR+DWjxnvzOgRUPUTnYOeYaIH8YV8/5mBhN2GVpnHIWEYUKEpqcM6t+ZhGC5UJ1ZbYU",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 1013057693,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
        """.trimIndent()

        // Keyset with the public keys of the keys from JSON_PRIVATE_KEYSET_WITH_MULTIPLE_KEYS.
        val JSON_PUBLIC_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 1013057693,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey",
                            "value": "EkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARogI4P/E3HzF6GSRNM4XlRwKBjGw81REj8ovlBno2uNvc8iIQC7Zjep7K4nPGJljgg6GCOrovBJcJRGWMsg8XLDTh0Cdw==",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 647048814,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey",
                            "value": "EkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARogYDMh2pw+/IEZ5OTcWsnl3k8QunjsB1spu2Ex71L82WEiIECb/Un5ANDbIFdOpf+fxK0DJiTno1XVKuJym1WCqZTz",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 418995680,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey",
                            "value": "EkQKBAgCEAMSOhI4CjB0eXBlLmdvb2dsZWFwaXMuY29tL2dvb2dsZS5jcnlwdG8udGluay5BZXNHY21LZXkSAhAQGAEYARohAMJtGycPgL1lcApnYiP3MbURUE5tkkOxdeiOUTxsclmLIiEA4aNiPurRhAnYMdpLS52MbOR+DWjxnvzOgRUPUTnYOeY=",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 1013057693,
                        "outputPrefixType": "TINK"
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
