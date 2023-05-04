# Tink-KMM

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ryunen344/tink-common/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.ryunen344/tink-common)
[![test](https://github.com/RyuNen344/tink-kmm/actions/workflows/test.yml/badge.svg?branch=main)](https://github.com/RyuNen344/tink-kmm/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/RyuNen344/tink-kmm/branch/main/graph/badge.svg?token=21Z06YR92T)](https://codecov.io/gh/RyuNen344/tink-kmm)
![badge-android](http://img.shields.io/badge/-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat)
![badge-silicon](http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg?style=flat)

This is a repositoy of [Google/Tink](https://github.com/google/tink) Mapper for KMM(Kotlin Multiplatform Mobile)

## Tink Primitives

| **Primitive**                                       | **Interfaces**                 |
| --------------------------------------------------- | ------------------------------ |
| Authenticated Encryption with Associated Data (AEAD)| AEAD                           |
| *Streaming* AEAD                                    | StreamingAEAD                  |
| *Deterministic* AEAD                                | DeterministicAEAD              |
| Message Authentication Code (MAC)                   | MAC                            |
| Pseudo Random Function Family (PRF)                 | Prf, PrfSet                    |
| Hybrid encryption                                   | HybridEncrypt, HybridDecrypt   |
| Digital signatures                                  | PublicKeySign, PublicKeyVerify |

## Supported primitives and their implementations

### Primitives supported by language

**Primitive**      | **Java** | **Objective-C** | **Tink-KMM** |
------------------ |:--------:|:---------------:|:------------:|
AEAD               |   yes    |       yes       |     yes      |
Streaming AEAD     |   yes    |     **no**      |    **no**    |
Deterministic AEAD |   yes    |       yes       |     yes      |
MAC                |   yes    |       yes       |     yes      |
PRF                |   yes    |     **no**      |    **no**    |
Digital signatures |   yes    |       yes       |     yes      |
Hybrid encryption  |   yes    |       yes       |     yes      |

### Primitive implementations supported by language

| **Primitive**       | **Implementation**                    | **Java** | **Objective-C** | **Tink-KMM** |
| ------------------- |---------------------------------------|:--------:|:---------------:|:------------:|
| AEAD                | AES-GCM                               |   yes    |       yes       |      yes     |
|                     | AES-GCM-SIV                           |   yes    |     **no**      |    **no**    |
|                     | AES-CTR-HMAC                          |   yes    |       yes       |      yes     |
|                     | AES-EAX                               |   yes    |       yes       |      yes     |
|                     | KMS Envelope                          |   yes    |     **no**      |    **no**    |
|                     | CHACHA20-POLY1305                     |   yes    |     **no**      |    **no**    |
|                     | XCHACHA20-POLY1305                    |   yes    |       yes       |      yes     |
| Streaming AEAD      | AES-GCM-HKDF-STREAMING                |   yes    |     **no**      |    **no**    |
|                     | AES-CTR-HMAC-STREAMING                |   yes    |     **no**      |    **no**    |
| Deterministic AEAD  | AES-SIV                               |   yes    |       yes       |      yes     |
| MAC                 | HMAC-SHA2                             |   yes    |       yes       |      yes     |
|                     | AES-CMAC                              |   yes    |       yes       |      yes     |
| PRF                 | HKDF-SHA2                             |   yes    |     **no**      |    **no**    |
|                     | HMAC-SHA2                             |   yes    |     **no**      |    **no**    |
|                     | AES-CMAC                              |   yes    |     **no**      |    **no**    |
| Digital Signatures  | ECDSA over NIST curves                |   yes    |       yes       |      yes     |
|                     | Ed25519                               |   yes    |       yes       |      yes     |
|                     | RSA-SSA-PKCS1                         |   yes    |       yes       |      yes     |
|                     | RSA-SSA-PSS                           |   yes    |       yes       |      yes     |
| Hybrid Encryption   | HPKE                                  |   yes    |     **no**      |    **no**    |
|                     | ECIES with AEAD and HKDF              |   yes    |       yes       |      yes     |
|                     | ECIES with DeterministicAEAD and HKDF |   yes    |     **no**      |    **no**    |
