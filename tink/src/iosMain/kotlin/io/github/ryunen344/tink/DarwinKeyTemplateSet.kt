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
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError

@Throws(GeneralSecurityException::class)
actual fun KeyTemplateSet.template(): KeyTemplate = memScoped {
    val error = alloc<ObjCObjectVar<NSError?>>()
    val template: KeyTemplate = when (this@template) {
        // AEAD
        KeyTemplateSet.AES128_GCM -> TINKAeadKeyTemplate(TINKAes128Gcm, error.ptr)
        KeyTemplateSet.AES128_GCM_RAW -> TINKAeadKeyTemplate(TINKAes128GcmNoPrefix, error.ptr)
        KeyTemplateSet.AES256_GCM -> TINKAeadKeyTemplate(TINKAes256Gcm, error.ptr)
        KeyTemplateSet.AES256_GCM_RAW -> TINKAeadKeyTemplate(TINKAes256GcmNoPrefix, error.ptr)
        KeyTemplateSet.AES128_CTR_HMAC_SHA256 -> TINKAeadKeyTemplate(TINKAes128CtrHmacSha256, error.ptr)
        KeyTemplateSet.AES256_CTR_HMAC_SHA256 -> TINKAeadKeyTemplate(TINKAes256CtrHmacSha256, error.ptr)
        KeyTemplateSet.AES128_EAX -> TINKAeadKeyTemplate(TINKAes128Eax, error.ptr)
        KeyTemplateSet.AES256_EAX -> TINKAeadKeyTemplate(TINKAes256Eax, error.ptr)
        KeyTemplateSet.XCHACHA20_POLY1305 -> TINKAeadKeyTemplate(TINKXChaCha20Poly1305, error.ptr)

        // Deterministic AEAD
        KeyTemplateSet.AES256_SIV -> TINKDeterministicAeadKeyTemplate(TINKAes256Siv, error.ptr)

        // MAC
        KeyTemplateSet.HMAC_SHA256_128BITTAG -> TINKMacKeyTemplate(TINKHmacSha256HalfSizeTag, error.ptr)
        KeyTemplateSet.HMAC_SHA256_256BITTAG -> TINKMacKeyTemplate(TINKHmacSha256, error.ptr)
        KeyTemplateSet.HMAC_SHA512_256BITTAG -> TINKMacKeyTemplate(TINKHmacSha512HalfSizeTag, error.ptr)
        KeyTemplateSet.HMAC_SHA512_512BITTAG -> TINKMacKeyTemplate(TINKHmacSha512, error.ptr)
        KeyTemplateSet.AES_CMAC -> TINKMacKeyTemplate(TINKAesCmac, error.ptr)

        // Digital Signatures
        KeyTemplateSet.ECDSA_P256 -> TINKSignatureKeyTemplate(TINKEcdsaP256, error.ptr)
        KeyTemplateSet.ECDSA_P384 -> TINKSignatureKeyTemplate(TINKEcdsaP384, error.ptr)
        KeyTemplateSet.ECDSA_P384_SHA384 -> TINKSignatureKeyTemplate(TINKEcdsaP384Sha384, error.ptr)
        KeyTemplateSet.ECDSA_P384_SHA512 -> TINKSignatureKeyTemplate(TINKEcdsaP384Sha512, error.ptr)
        KeyTemplateSet.ECDSA_P521 -> TINKSignatureKeyTemplate(TINKEcdsaP521, error.ptr)
        KeyTemplateSet.ECDSA_P256_IEEE_P1363 -> TINKSignatureKeyTemplate(TINKEcdsaP256Ieee, error.ptr)
        KeyTemplateSet.ECDSA_P384_IEEE_P1363 -> TINKSignatureKeyTemplate(TINKEcdsaP384Ieee, error.ptr)
        KeyTemplateSet.ECDSA_P521_IEEE_P1363 -> TINKSignatureKeyTemplate(TINKEcdsaP521Ieee, error.ptr)
        KeyTemplateSet.ED25519 -> TINKSignatureKeyTemplate(TINKEd25519, error.ptr)
        KeyTemplateSet.RSA_SSA_PKCS1_3072_SHA256_F4 -> TINKSignatureKeyTemplate(
            TINKRsaSsaPkcs13072Sha256F4,
            error.ptr
        )

        KeyTemplateSet.RSA_SSA_PKCS1_4096_SHA512_F4 -> TINKSignatureKeyTemplate(
            TINKRsaSsaPkcs14096Sha512F4,
            error.ptr
        )

        KeyTemplateSet.RSA_SSA_PSS_3072_SHA256_SHA256_32_F4 -> TINKSignatureKeyTemplate(
            TINKRsaSsaPss3072Sha256Sha256F4,
            error.ptr
        )

        KeyTemplateSet.RSA_SSA_PSS_4096_SHA512_SHA512_64_F4 -> TINKSignatureKeyTemplate(
            TINKRsaSsaPss4096Sha512Sha512F4,
            error.ptr
        )

        // Hybrid Encryption
        KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM -> TINKHybridKeyTemplate(
            TINKEciesP256HkdfHmacSha256Aes128Gcm,
            error.ptr
        )

        KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256 -> TINKHybridKeyTemplate(
            TINKEciesP256HkdfHmacSha256Aes128CtrHmacSha256,
            error.ptr
        )
    }

    error.value?.let { throw GeneralSecurityException(cause = it.asThrowable()) }
    return@memScoped template
}
