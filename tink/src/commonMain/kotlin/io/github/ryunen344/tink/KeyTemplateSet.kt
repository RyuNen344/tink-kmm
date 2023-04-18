package io.github.ryunen344.tink

import io.github.ryunen344.tink.exception.GeneralSecurityException

enum class KeyTemplateSet(val value: String) {
    // AEAD
    AES128_GCM("AES128_GCM"),
    AES128_GCM_RAW("AES128_GCM_RAW"),
    AES256_GCM("AES256_GCM"),
    AES256_GCM_RAW("AES256_GCM_RAW"),
    AES128_CTR_HMAC_SHA256("AES128_CTR_HMAC_SHA256"),
    AES256_CTR_HMAC_SHA256("AES256_CTR_HMAC_SHA256"),
    AES128_EAX("AES128_EAX"),
    AES256_EAX("AES256_EAX"),
    XCHACHA20_POLY1305("XCHACHA20_POLY1305"),

    // Deterministic AEAD
    AES256_SIV("AES256_SIV"),

    // MAC
    HMAC_SHA256_128BITTAG("HMAC_SHA256_128BITTAG"),
    HMAC_SHA256_256BITTAG("HMAC_SHA256_256BITTAG"),
    HMAC_SHA512_256BITTAG("HMAC_SHA512_256BITTAG"),
    HMAC_SHA512_512BITTAG("HMAC_SHA512_512BITTAG"),
    AES_CMAC("AES_CMAC"),

    // Digital Signatures
    ECDSA_P256("ECDSA_P256"),
    ECDSA_P384("ECDSA_P384"),
    ECDSA_P384_SHA384("ECDSA_P384_SHA384"),
    ECDSA_P384_SHA512("ECDSA_P384_SHA512"),
    ECDSA_P521("ECDSA_P521"),
    ECDSA_P256_IEEE_P1363("ECDSA_P256_IEEE_P1363"),
    ECDSA_P384_IEEE_P1363("ECDSA_P384_IEEE_P1363"),
    ECDSA_P521_IEEE_P1363("ECDSA_P521_IEEE_P1363"),
    ED25519("ED25519"),
    RSA_SSA_PKCS1_3072_SHA256_F4("RSA_SSA_PKCS1_3072_SHA256_F4"),
    RSA_SSA_PKCS1_4096_SHA512_F4("RSA_SSA_PKCS1_4096_SHA512_F4"),
    RSA_SSA_PSS_3072_SHA256_SHA256_32_F4("RSA_SSA_PSS_3072_SHA256_SHA256_32_F4"),
    RSA_SSA_PSS_4096_SHA512_SHA512_64_F4("RSA_SSA_PSS_4096_SHA512_SHA512_64_F4"),

    // Hybrid Encryption
    ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM("ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM"),
    ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256("ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256"),
    ;
}

@Throws(GeneralSecurityException::class)
expect fun KeyTemplateSet.template(): KeyTemplate
