package io.github.ryunen344.tink.signature

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
import kotlin.test.assertFailsWith

class SignatureTest {

    private fun verify(
        set: KeyTemplateSet,
        data: ByteArray = "data".encodeToByteArray(),
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val publicHandle = privateHandle.publicKeysetHandle()

        val signer = privateHandle.getPrimitive(PublicKeySign::class)
        val verifier = publicHandle.getPrimitive(PublicKeyVerify::class)

        val signature = signer.sign(data)
        verifier.verify(signature, data)
    }

    private fun invalidData(
        set: KeyTemplateSet,
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val publicHandle = privateHandle.publicKeysetHandle()

        val signer = privateHandle.getPrimitive(PublicKeySign::class)
        val verifier = publicHandle.getPrimitive(PublicKeyVerify::class)

        val data = "data".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()
        val signature = signer.sign(data)

        assertFailsWith<GeneralSecurityException> {
            verifier.verify(signature, invalid)
        }
    }

    private fun invalidSignature(
        set: KeyTemplateSet,
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val publicHandle = privateHandle.publicKeysetHandle()
        val verifier = publicHandle.getPrimitive(PublicKeyVerify::class)

        val data = "data".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            verifier.verify(invalid, data)
        }
    }

    private fun emptySignature(
        set: KeyTemplateSet,
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val publicHandle = privateHandle.publicKeysetHandle()
        val verifier = publicHandle.getPrimitive(PublicKeyVerify::class)

        val data = "data".encodeToByteArray()
        val empty = "".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            verifier.verify(empty, data)
        }
    }

    private fun match(
        set: KeyTemplateSet,
    ) {
        val privateHandle = KeysetHandleGenerator.generateNew(set.template())
        val signer = privateHandle.getPrimitive(PublicKeySign::class)
        val data = "data".encodeToByteArray()
        val signature = signer.sign(data)

        val otherHandle = KeysetHandleGenerator.generateNew(set.template())
        val otherVerifier = otherHandle.publicKeysetHandle().getPrimitive(PublicKeyVerify::class)

        assertFailsWith<GeneralSecurityException> {
            otherVerifier.verify(signature, data)
        }
    }

    @BeforeTest
    fun setup() {
        SignatureConfig.register()
    }

    @Test
    fun test_verify_ECDSA_P256_then_success() = verify(KeyTemplateSet.ECDSA_P256)

    @Test
    fun test_verify_ECDSA_P256_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P256, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P256_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P256)

    @Test
    fun test_verify_ECDSA_P256_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P256)

    @Test
    fun test_verify_ECDSA_P256_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P256)

    @Test
    fun test_verify_ECDSA_P256_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P256)

    @Test
    fun test_verify_ECDSA_P384_then_success() = verify(KeyTemplateSet.ECDSA_P384)

    @Test
    fun test_verify_ECDSA_P384_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P384, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P384_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P384)

    @Test
    fun test_verify_ECDSA_P384_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P384)

    @Test
    fun test_verify_ECDSA_P384_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P384)

    @Test
    fun test_verify_ECDSA_P384_SHA256_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P384)

    @Test
    fun test_verify_ECDSA_P384_SHA384_then_success() = verify(KeyTemplateSet.ECDSA_P384_SHA384)

    @Test
    fun test_verify_ECDSA_P384_SHA384_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P384_SHA384, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P384_SHA384_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P384_SHA384)

    @Test
    fun test_verify_ECDSA_P384_SHA384_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P384_SHA384)

    @Test
    fun test_verify_ECDSA_P384_SHA384_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P384_SHA384)

    @Test
    fun test_verify_ECDSA_P384_SHA384_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P384_SHA384)

    @Test
    fun test_verify_ECDSA_P384_SHA512_then_success() = verify(KeyTemplateSet.ECDSA_P384_SHA512)

    @Test
    fun test_verify_ECDSA_P384_SHA512_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P384_SHA512, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P384_SHA512_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P384_SHA512)

    @Test
    fun test_verify_ECDSA_P384_SHA512_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P384_SHA512)

    @Test
    fun test_verify_ECDSA_P384_SHA512_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P384_SHA512)

    @Test
    fun test_verify_ECDSA_P384_SHA512_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P384_SHA512)

    @Test
    fun test_verify_ECDSA_P521_then_success() = verify(KeyTemplateSet.ECDSA_P521)

    @Test
    fun test_verify_ECDSA_P521_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P521, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P521_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P521)

    @Test
    fun test_verify_ECDSA_P521_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P521)

    @Test
    fun test_verify_ECDSA_P521_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P521)

    @Test
    fun test_verify_ECDSA_P521_SHA256_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P521)

    @Test
    fun test_verify_ECDSA_P256_IEEE_P1363_then_success() = verify(KeyTemplateSet.ECDSA_P256_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P256_IEEE_P1363_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P256_IEEE_P1363, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P256_IEEE_P1363_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P256_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P256_IEEE_P1363_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P256_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P256_IEEE_P1363_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P256_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P256_IEEE_P1363_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P256_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P384_IEEE_P1363_then_success() = verify(KeyTemplateSet.ECDSA_P384_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P384_IEEE_P1363_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P384_IEEE_P1363, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P384_IEEE_P1363_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P384_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P384_IEEE_P1363_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P384_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P384_IEEE_P1363_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P384_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P384_IEEE_P1363_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P384_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P521_IEEE_P1363_then_success() = verify(KeyTemplateSet.ECDSA_P521_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P521_IEEE_P1363_given_empty_data_then_success() =
        verify(KeyTemplateSet.ECDSA_P521_IEEE_P1363, "".encodeToByteArray())

    @Test
    fun test_verify_ECDSA_P521_IEEE_P1363_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ECDSA_P521_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P521_IEEE_P1363_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ECDSA_P521_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P521_IEEE_P1363_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ECDSA_P521_IEEE_P1363)

    @Test
    fun test_verify_ECDSA_P521_IEEE_P1363_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ECDSA_P521_IEEE_P1363)

    @Test
    fun test_verify_ED25519_then_success() = verify(KeyTemplateSet.ED25519)

    @Test
    fun test_verify_ED25519_given_empty_data_then_success() =
        verify(KeyTemplateSet.ED25519, "".encodeToByteArray())

    @Test
    fun test_verify_ED25519_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.ED25519)

    @Test
    fun test_verify_ED25519_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.ED25519)

    @Test
    fun test_verify_ED25519_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.ED25519)

    @Test
    fun test_verify_ED25519_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.ED25519)

    @Test
    fun test_verify_RSA_SSA_PKCS1_3072_SHA256_F4_then_success() = verify(KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_3072_SHA256_F4_given_empty_data_then_success() =
        verify(KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4, "".encodeToByteArray())

    @Test
    fun test_verify_RSA_SSA_PKCS1_3072_SHA256_F4_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_3072_SHA256_F4_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_3072_SHA256_F4_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_3072_SHA256_F4_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_4096_SHA512_F4_then_success() = verify(KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_4096_SHA512_F4_given_empty_data_then_success() =
        verify(KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4, "".encodeToByteArray())

    @Test
    fun test_verify_RSA_SSA_PKCS1_4096_SHA512_F4_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_4096_SHA512_F4_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_4096_SHA512_F4_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4)

    @Test
    fun test_verify_RSA_SSA_PKCS1_4096_SHA512_F4_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_3072_SHA256_SHA256_32_F4_then_success() =
        verify(KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_3072_SHA256_SHA256_32_F4_given_empty_data_then_success() =
        verify(KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4, "".encodeToByteArray())

    @Test
    fun test_verify_RSA_SSA_PSS_3072_SHA256_SHA256_32_F4_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_3072_SHA256_SHA256_32_F4_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_3072_SHA256_SHA256_32_F4_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_3072_SHA256_SHA256_32_F4_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_4096_SHA512_SHA512_64_F4_then_success() =
        verify(KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_4096_SHA512_SHA512_64_F4_given_empty_data_then_success() =
        verify(KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4, "".encodeToByteArray())

    @Test
    fun test_verify_RSA_SSA_PSS_4096_SHA512_SHA512_64_F4_given_invalid_data_then_throw_error() =
        invalidData(KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_4096_SHA512_SHA512_64_F4_given_invalid_signature_then_throw_error() =
        invalidSignature(KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_4096_SHA512_SHA512_64_F4_given_empty_signature_then_throw_error() =
        emptySignature(KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4)

    @Test
    fun test_verify_RSA_SSA_PSS_4096_SHA512_SHA512_64_F4_when_not_match_keyset_then_throw_error() =
        match(KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4)

    @Test
    fun test_verify_given_json_keyset_then_success() {
        val privateHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PRIVATE_KEYSET))
        val publicHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PUBLIC_KEYSET))

        val signer = privateHandle.getPrimitive(PublicKeySign::class)
        val verifier = publicHandle.getPrimitive(PublicKeyVerify::class)

        val data = "data".encodeToByteArray()
        val signature = signer.sign(data)
        verifier.verify(signature, data)
    }

    @Test
    fun test_verify_given_multiple_json_keyset_then_success() {
        val privateHandle =
            KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PRIVATE_KEYSET_WITH_MULTIPLE_KEYS))
        val publicHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PUBLIC_KEYSET_WITH_MULTIPLE_KEYS))

        val signer = privateHandle.getPrimitive(PublicKeySign::class)
        val verifier = publicHandle.getPrimitive(PublicKeyVerify::class)

        val data = "data".encodeToByteArray()
        val signature = signer.sign(data)
        verifier.verify(signature, data)

        val otherHandle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_PRIVATE_KEYSET))
        val otherSigner = otherHandle.getPrimitive(PublicKeySign::class)

        val otherData = "other_data".encodeToByteArray()
        val otherSignature = otherSigner.sign(otherData)
        verifier.verify(otherSignature, otherData)
    }

    @Test
    fun test_getPrimitive_given_NonSignatureKeyset_then_throws_error() {
        DeterministicAeadConfig.register()
        val handle = KeysetHandleGenerator.readClearText(JsonKeysetReader(JSON_DAEAD_KEYSET))
        handle.getPrimitive(DeterministicAead::class)

        assertFailsWith<GeneralSecurityException> {
            handle.getPrimitive(PublicKeySign::class)
        }
        assertFailsWith<GeneralSecurityException> {
            handle.getPrimitive(PublicKeyVerify::class)
        }
    }

    private companion object {
        val JSON_PRIVATE_KEYSET = """
            {
                "primaryKeyId": 775870498,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EcdsaPrivateKey",
                            "value": "GiA/E6s6KksNXrEd9hLdStvhsmdsONgpSODH/rZsBbBDehJMIiApA+NmYivxRfhMuvTKZAwqETmn+WagBP/reucEjEvXkRog1AJ5GBzf+n27xnj9KcoGllF9NIFfQrDEP99FNH+Cne4SBhgCEAIIAw==",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 775870498,
                        "outputPrefixType": "TINK"
                    }
                ]
            }
        """.trimIndent()

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

        val JSON_PRIVATE_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 1641152230,
                "key": [
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EcdsaPrivateKey",
                            "value": "GiA/E6s6KksNXrEd9hLdStvhsmdsONgpSODH/rZsBbBDehJMIiApA+NmYivxRfhMuvTKZAwqETmn+WagBP/reucEjEvXkRog1AJ5GBzf+n27xnj9KcoGllF9NIFfQrDEP99FNH+Cne4SBhgCEAIIAw==",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 775870498,
                        "outputPrefixType": "TINK"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey",
                            "value": "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",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 1641152230,
                        "outputPrefixType": "RAW"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EcdsaPrivateKey",
                            "value": "GiBCX+pnT5vwku4z7jfD4OTgvRtft3S4KuYHovWsQrlTPhJMIiAzcsfCVUzGZ13oTmMLxBYd8wFM5G+dgCXMeF8tYXayrRogGOzqO4xtS0H4wl/5M/QUkLDnpnmt2TqIiQlFk0vAdckSBhgCEAIIAw==",
                            "keyMaterialType": "ASYMMETRIC_PRIVATE"
                        },
                        "status": "ENABLED",
                        "keyId": 857170602,
                        "outputPrefixType": "LEGACY"
                    }
                ]
            }
        """.trimIndent()

        val JSON_PUBLIC_KEYSET_WITH_MULTIPLE_KEYS = """
            {
                "primaryKeyId": 1641152230,
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
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PublicKey",
                            "value": "IgMBAAEagATs8xew2XBre4TqIOeaGagCvO6DOaclGHK0HNwvh0aGsg4zBCdxoc18M+xNbJ+MuFrpDRN8E+IMabQGr78jKBAzdYqh9Ue4fYnv5u3halLQBFJBuV9eJdrO9gAPiPgtW0ET5O4HJsYH+dra6L8bjzjnaKn4UblXsbqwR+TGKzcROBm3JMHqgMNddrKYygaWJway5zroFY9GKSjg6+dLw9GmLTKht3DrmC2CAOusP8ZLgf4VWrWQQ6tUoULhRlljJqKZieXkhLU70/PW+bl6mJxam0Wh0AWjk3QLMr+HDByhxvTYK5SDE42tLdnKnD4bHIx2BEjY/8KmKGbtRII7M3GMcSY2C3ta7C1AKFzhkR2VnUpcVdcQFl/qHAs0qTkmHsw6Yl8xJT0v0nVOqSt+WzbrxPQURSYPSdyItEeh6qNoP1xbFqHz0XqvVS4Kj4tWxCnjLuD+pebFS8u8VHok9nAfH2LJNtUR7nj8/ZtyBu20QhiOqCzkUu3Vwbp2qMqIXGULxItkmwyDXGlk1NV7uOnNfU63ScrzJj9SYtSuCrQAcffU3cv1ru+Dq/+4jmR1ZlmgBKMGVXMs2B4EKhAZfAV1M0Q14NgIBZcSA7rkrWG6rdRygmOO8JMPS2Wt1M1fur3XlNLd7j3GAlPeUHhPKcCjYWCz051m1qi9UUqmITkroxICCAQ=",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 1641152230,
                        "outputPrefixType": "RAW"
                    },
                    {
                        "keyData": {
                            "typeUrl": "type.googleapis.com/google.crypto.tink.EcdsaPublicKey",
                            "value": "IiAzcsfCVUzGZ13oTmMLxBYd8wFM5G+dgCXMeF8tYXayrRogGOzqO4xtS0H4wl/5M/QUkLDnpnmt2TqIiQlFk0vAdckSBhgCEAIIAw==",
                            "keyMaterialType": "ASYMMETRIC_PUBLIC"
                        },
                        "status": "ENABLED",
                        "keyId": 857170602,
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
