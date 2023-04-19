package io.github.ryunen344.tink

import com.google.crypto.tink.TINKAeadKeyTemplate
import com.google.crypto.tink.TINKAes128CtrHmacSha256
import com.google.crypto.tink.TINKAes128Eax
import com.google.crypto.tink.TINKAes128Gcm
import com.google.crypto.tink.TINKAes128GcmNoPrefix
import com.google.crypto.tink.TINKAes256CtrHmacSha256
import com.google.crypto.tink.TINKAes256Eax
import com.google.crypto.tink.TINKAes256Gcm
import com.google.crypto.tink.TINKAes256GcmNoPrefix
import com.google.crypto.tink.TINKAes256Siv
import com.google.crypto.tink.TINKAesCmac
import com.google.crypto.tink.TINKDeterministicAeadKeyTemplate
import com.google.crypto.tink.TINKEcdsaP256
import com.google.crypto.tink.TINKEcdsaP256Ieee
import com.google.crypto.tink.TINKEcdsaP384
import com.google.crypto.tink.TINKEcdsaP384Ieee
import com.google.crypto.tink.TINKEcdsaP384Sha384
import com.google.crypto.tink.TINKEcdsaP384Sha512
import com.google.crypto.tink.TINKEcdsaP521
import com.google.crypto.tink.TINKEcdsaP521Ieee
import com.google.crypto.tink.TINKEciesP256HkdfHmacSha256Aes128CtrHmacSha256
import com.google.crypto.tink.TINKEciesP256HkdfHmacSha256Aes128Gcm
import com.google.crypto.tink.TINKEd25519
import com.google.crypto.tink.TINKHmacSha256
import com.google.crypto.tink.TINKHmacSha256HalfSizeTag
import com.google.crypto.tink.TINKHmacSha512
import com.google.crypto.tink.TINKHmacSha512HalfSizeTag
import com.google.crypto.tink.TINKHybridKeyTemplate
import com.google.crypto.tink.TINKMacKeyTemplate
import com.google.crypto.tink.TINKRsaSsaPkcs13072Sha256F4
import com.google.crypto.tink.TINKRsaSsaPkcs14096Sha512F4
import com.google.crypto.tink.TINKRsaSsaPss3072Sha256Sha256F4
import com.google.crypto.tink.TINKRsaSsaPss4096Sha512Sha512F4
import com.google.crypto.tink.TINKSignatureKeyTemplate
import com.google.crypto.tink.TINKXChaCha20Poly1305
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.util.asThrowable
import io.github.ryunen344.tink.util.memScopedInstance
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

@Throws(GeneralSecurityException::class)
actual fun KeyTemplateSet.template(): KeyTemplate = memScopedInstance(
    block = {
        when (this@template) {
            // AEAD
            KeyTemplateSet.AES128_GCM -> TINKAeadKeyTemplate(TINKAes128Gcm, it.ptr)
            KeyTemplateSet.AES128_GCM_RAW -> TINKAeadKeyTemplate(TINKAes128GcmNoPrefix, it.ptr)
            KeyTemplateSet.AES256_GCM -> TINKAeadKeyTemplate(TINKAes256Gcm, it.ptr)
            KeyTemplateSet.AES256_GCM_RAW -> TINKAeadKeyTemplate(TINKAes256GcmNoPrefix, it.ptr)
            KeyTemplateSet.AES128_CTR_HMAC_SHA256 -> TINKAeadKeyTemplate(TINKAes128CtrHmacSha256, it.ptr)
            KeyTemplateSet.AES256_CTR_HMAC_SHA256 -> TINKAeadKeyTemplate(TINKAes256CtrHmacSha256, it.ptr)
            KeyTemplateSet.AES128_EAX -> TINKAeadKeyTemplate(TINKAes128Eax, it.ptr)
            KeyTemplateSet.AES256_EAX -> TINKAeadKeyTemplate(TINKAes256Eax, it.ptr)
            KeyTemplateSet.XCHACHA20_POLY1305 -> TINKAeadKeyTemplate(TINKXChaCha20Poly1305, it.ptr)

            // Deterministic AEAD
            KeyTemplateSet.AES256_SIV -> TINKDeterministicAeadKeyTemplate(TINKAes256Siv, it.ptr)

            // MAC
            KeyTemplateSet.HMAC_SHA256_128BITTAG -> TINKMacKeyTemplate(TINKHmacSha256HalfSizeTag, it.ptr)
            KeyTemplateSet.HMAC_SHA256_256BITTAG -> TINKMacKeyTemplate(TINKHmacSha256, it.ptr)
            KeyTemplateSet.HMAC_SHA512_256BITTAG -> TINKMacKeyTemplate(TINKHmacSha512HalfSizeTag, it.ptr)
            KeyTemplateSet.HMAC_SHA512_512BITTAG -> TINKMacKeyTemplate(TINKHmacSha512, it.ptr)
            KeyTemplateSet.AES_CMAC -> TINKMacKeyTemplate(TINKAesCmac, it.ptr)

            // Digital Signatures
            KeyTemplateSet.ECDSA_P256 -> TINKSignatureKeyTemplate(TINKEcdsaP256, it.ptr)
            KeyTemplateSet.ECDSA_P384 -> TINKSignatureKeyTemplate(TINKEcdsaP384, it.ptr)
            KeyTemplateSet.ECDSA_P384_SHA384 -> TINKSignatureKeyTemplate(TINKEcdsaP384Sha384, it.ptr)
            KeyTemplateSet.ECDSA_P384_SHA512 -> TINKSignatureKeyTemplate(TINKEcdsaP384Sha512, it.ptr)
            KeyTemplateSet.ECDSA_P521 -> TINKSignatureKeyTemplate(TINKEcdsaP521, it.ptr)
            KeyTemplateSet.ECDSA_P256_IEEE_P1363 -> TINKSignatureKeyTemplate(TINKEcdsaP256Ieee, it.ptr)
            KeyTemplateSet.ECDSA_P384_IEEE_P1363 -> TINKSignatureKeyTemplate(TINKEcdsaP384Ieee, it.ptr)
            KeyTemplateSet.ECDSA_P521_IEEE_P1363 -> TINKSignatureKeyTemplate(TINKEcdsaP521Ieee, it.ptr)
            KeyTemplateSet.ED25519 -> TINKSignatureKeyTemplate(TINKEd25519, it.ptr)
            KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4 -> TINKSignatureKeyTemplate(TINKRsaSsaPkcs13072Sha256F4, it.ptr)
            KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4 -> TINKSignatureKeyTemplate(TINKRsaSsaPkcs14096Sha512F4, it.ptr)

            KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4 -> TINKSignatureKeyTemplate(
                TINKRsaSsaPss3072Sha256Sha256F4,
                it.ptr
            )

            KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4 -> TINKSignatureKeyTemplate(
                TINKRsaSsaPss4096Sha512Sha512F4,
                it.ptr
            )

            // Hybrid Encryption
            KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM -> TINKHybridKeyTemplate(
                TINKEciesP256HkdfHmacSha256Aes128Gcm,
                it.ptr
            )

            KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256 -> TINKHybridKeyTemplate(
                TINKEciesP256HkdfHmacSha256Aes128CtrHmacSha256,
                it.ptr
            )
        }
    },
    onError = { throw GeneralSecurityException(cause = it.asThrowable()) }
)
