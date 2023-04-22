package io.github.ryunen344.tink.mac

import io.github.ryunen344.tink.JsonKeysetReader
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.daead.register
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.readClearText
import io.github.ryunen344.tink.template
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class MacTest {

    private fun verify(
        set: KeyTemplateSet,
        data: ByteArray = "data".encodeToByteArray(),
    ) {
        val handle = KeysetHandleGenerator.generateNew(set.template())
        val mac = handle.getPrimitive(Mac::class)
        val tag = mac.computeMac(data)
        mac.verifyMac(tag, data)
    }

    private fun invalid(
        set: KeyTemplateSet,
    ) {
        val handle = KeysetHandleGenerator.generateNew(set.template())
        val mac = handle.getPrimitive(Mac::class)
        val data = "data".encodeToByteArray()
        val tag = mac.computeMac(data)

        val invalid = "invalid".encodeToByteArray()
        val empty = "".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> { mac.verifyMac(invalid, data) }
        assertFailsWith<GeneralSecurityException> { mac.verifyMac(tag, invalid) }
        assertFailsWith<GeneralSecurityException> { mac.verifyMac(empty, data) }
        assertFailsWith<GeneralSecurityException> { mac.verifyMac(tag, empty) }
    }

    private fun other(
        set: KeyTemplateSet,
    ) {
        val handle = KeysetHandleGenerator.generateNew(set.template())
        val mac = handle.getPrimitive(Mac::class)
        val data = "data".encodeToByteArray()
        val tag = mac.computeMac(data)

        val otherHandle = KeysetHandleGenerator.generateNew(set.template())
        val otherMac = otherHandle.getPrimitive(Mac::class)

        assertFailsWith<GeneralSecurityException> { otherMac.verifyMac(tag, data) }
    }

    @BeforeTest
    fun setup() {
        MacConfig.register()
    }

    @Test
    fun test_verify_HMAC_SHA256_128BITTAG_then_success() = verify(KeyTemplateSet.HMAC_SHA256_128BITTAG)

    @Test
    fun test_verify_HMAC_SHA256_128BITTAG_given_empty_data_then_success() =
        verify(KeyTemplateSet.HMAC_SHA256_128BITTAG, "".encodeToByteArray())

    @Test
    fun test_verify_HMAC_SHA256_128BITTAG_given_invalid_data_then_throw_error() =
        invalid(KeyTemplateSet.HMAC_SHA256_128BITTAG)

    @Test
    fun test_verify_HMAC_SHA256_128BITTAG_when_verify_other_keyset_then_throw_error() =
        other(KeyTemplateSet.HMAC_SHA256_128BITTAG)

    @Test
    fun test_verify_HMAC_SHA256_256BITTAG_then_success() = verify(KeyTemplateSet.HMAC_SHA256_256BITTAG)

    @Test
    fun test_verify_HMAC_SHA256_256BITTAG_then_given_empty_data_then_success() =
        verify(KeyTemplateSet.HMAC_SHA256_256BITTAG, "".encodeToByteArray())

    @Test
    fun test_verify_HMAC_SHA256_256BITTAG_then_given_invalid_data_then_throw_error() =
        invalid(KeyTemplateSet.HMAC_SHA256_256BITTAG)

    @Test
    fun test_verify_HMAC_SHA256_256BITTAG_then_when_verify_other_keyset_then_throw_error() =
        other(KeyTemplateSet.HMAC_SHA256_256BITTAG)

    @Test
    fun test_verify_HMAC_SHA512_256BITTAG_then_success() = verify(KeyTemplateSet.HMAC_SHA512_256BITTAG)

    @Test
    fun test_verify_HMAC_SHA512_256BITTAG_then_given_empty_data_then_success() =
        verify(KeyTemplateSet.HMAC_SHA512_256BITTAG, "".encodeToByteArray())

    @Test
    fun test_verify_HMAC_SHA512_256BITTAG_then_given_invalid_data_then_throw_error() =
        invalid(KeyTemplateSet.HMAC_SHA512_256BITTAG)

    @Test
    fun test_verify_HMAC_SHA512_256BITTAG_when_verify_other_keyset_then_throw_error() =
        other(KeyTemplateSet.HMAC_SHA512_256BITTAG)

    @Test
    fun test_verify_HMAC_SHA512_512BITTAG_then_success() = verify(KeyTemplateSet.HMAC_SHA512_512BITTAG)

    @Test
    fun test_verify_HMAC_SHA512_512BITTAG_then_given_empty_data_then_success() =
        verify(KeyTemplateSet.HMAC_SHA512_512BITTAG, "".encodeToByteArray())

    @Test
    fun test_verify_HMAC_SHA512_512BITTAG_then_given_invalid_data_then_throw_error() =
        invalid(KeyTemplateSet.HMAC_SHA512_512BITTAG)

    @Test
    fun test_verify_HMAC_SHA512_512BITTAG_when_verify_other_keyset_then_throw_error() =
        other(KeyTemplateSet.HMAC_SHA512_512BITTAG)

    @Test
    fun test_verify_AES_CMAC_then_success() = verify(KeyTemplateSet.AES_CMAC)

    @Test
    fun test_verify_AES_CMAC_then_given_empty_data_then_success() =
        verify(KeyTemplateSet.AES_CMAC, "".encodeToByteArray())

    @Test
    fun test_verify_AES_CMAC_then_given_invalid_data_then_throw_error() = invalid(KeyTemplateSet.AES_CMAC)

    @Test
    fun test_verify_AES_CMAC_when_verify_other_keyset_then_throw_error() = other(KeyTemplateSet.AES_CMAC)

    @Test
    fun test_verify_given_json_keyset_then_success() {
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_MAC_KEYSET))
        val mac = handle.getPrimitive(Mac::class)
        val data = "data".encodeToByteArray()
        val tag = mac.computeMac(data)
        mac.verifyMac(tag, data)
    }

    @Test
    fun test_verify_given_multiple_keyset_then_success() {
        val primaryHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_MAC_KEYSET_WITH_MULTIPLE_KEYS))
        val primaryMac = primaryHandle.getPrimitive(Mac::class)
        val data = "data".encodeToByteArray()
        val tag = primaryMac.computeMac(data)
        primaryMac.verifyMac(tag, data)

        // Also test that mac can verify tags computed with a non-primary key. We use
        // JSON_MAC_KEYSET to compute a tag with the first key.
        val secondaryHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_MAC_KEYSET))
        val secondaryMac = secondaryHandle.getPrimitive(Mac::class)
        val secondaryTag = secondaryMac.computeMac(data)
        primaryMac.verifyMac(secondaryTag, data)
    }

    @Test
    fun test_getPrimitive_given_NonMacKeyset_then_throws_error() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        assertFailsWith<GeneralSecurityException> {
            handle.getPrimitive(Mac::class)
        }
    }

    private companion object {
        val JSON_MAC_KEYSET = """
            {
                "primaryKeyId": 207420876,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HmacKey",
                            "value": "GiAPii+kxtLpvCARQpftFLt4R+O6ARsyhTR7SkCCGt0bHRIEEBAIAw==",
                            "keyMaterialType": "SYMMETRIC"
                        },
                        "status": "ENABLED",
                        "keyId": 207420876,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
        """.trimIndent()

        val JSON_MAC_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 2054715504,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HmacKey",
                            "value": "GiAPii+kxtLpvCARQpftFLt4R+O6ARsyhTR7SkCCGt0bHRIEEBAIAw==",
                            "keyMaterialType": "SYMMETRIC"
                        },
                        "status": "ENABLED",
                        "keyId": 207420876,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.AesCmacKey",
                            "value": "GgIIEBIgLaZ/6QXYeqZB8F4zHTRJU5k6TF5xvlSX9ZVLVA09UY0=",
                            "keyMaterialType": "SYMMETRIC"
                        },
                        "status": "ENABLED",
                        "keyId": 2054715504,
                        "outputPrefixType": "RAW"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HmacKey",
                            "value": "GkCCIGYpFz3mj8wnTH3Ca81F1sQ7JEMxoE8B2nKiND7LrKfbaUx+/qqDXUPVjkzC9XdbjsaEqc9yI+RKyITef+eUEgQQQAgE",
                            "keyMaterialType": "SYMMETRIC"
                        },
                        "status": "ENABLED",
                        "keyId": 1540103625,
                        "outputPrefixType": "LEGACY"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.HmacKey",
                            "value": "GkA8u6JKtInsySJDZO4j6TLoIvLuGAeAZHDZoTlST0aZZ8gZZViHogzWTqti2Vlp3ccy+OdN6lhMxSiphcPaR5OiEgQQIAgE",
                            "keyMaterialType": "SYMMETRIC"
                        },
                        "status": "ENABLED",
                        "keyId": 570162478,
                        "outputPrefixType": "CRUNCHY"
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
