# Tink-KMM

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ryunen344.tink/tink-common/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.ryunen344/tink-common)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![test](https://github.com/RyuNen344/tink-kmm/actions/workflows/test.yml/badge.svg?branch=main)](https://github.com/RyuNen344/tink-kmm/actions/workflows/test.yml)
[![codecov](https://codecov.io/gh/RyuNen344/tink-kmm/branch/main/graph/badge.svg?token=21Z06YR92T)](https://codecov.io/gh/RyuNen344/tink-kmm)
![badge-android](http://img.shields.io/badge/-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat)
![badge-silicon](http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg?style=flat)

This is a repositoy of [Google/Tink](https://github.com/google/tink) Mapper for KMM(Kotlin Multiplatform Mobile)
This wrapper library allows you to use Tink Primitive Encryption in your Kotlin Multiplatform Mobile project.

## Why this library?

## Tink Primitives

| **Primitive**                                        | **Interfaces**                 |
| ---------------------------------------------------- | ------------------------------ |
| Authenticated Encryption with Associated Data (AEAD) | AEAD                           |
| _Streaming_ AEAD                                     | StreamingAEAD                  |
| _Deterministic_ AEAD                                 | DeterministicAEAD              |
| Message Authentication Code (MAC)                    | MAC                            |
| Pseudo Random Function Family (PRF)                  | Prf, PrfSet                    |
| Hybrid encryption                                    | HybridEncrypt, HybridDecrypt   |
| Digital signatures                                   | PublicKeySign, PublicKeyVerify |

### Supported primitives and their implementations

#### Primitives supported by language

| **Primitive**      | **Java** | **Objective-C** | **Tink-KMM** |
| ------------------ | :------: | :-------------: | :----------: |
| AEAD               |   yes    |       yes       |     yes      |
| Streaming AEAD     |   yes    |     **no**      |    **no**    |
| Deterministic AEAD |   yes    |       yes       |     yes      |
| MAC                |   yes    |       yes       |     yes      |
| PRF                |   yes    |     **no**      |    **no**    |
| Digital signatures |   yes    |       yes       |     yes      |
| Hybrid encryption  |   yes    |       yes       |     yes      |

#### Primitive implementations supported by language

| **Primitive**      | **Implementation**                    | **Java** | **Objective-C** | **Tink-KMM** |
| ------------------ | ------------------------------------- | :------: | :-------------: | :----------: |
| AEAD               | AES-GCM                               |   yes    |       yes       |     yes      |
|                    | AES-GCM-SIV                           |   yes    |     **no**      |    **no**    |
|                    | AES-CTR-HMAC                          |   yes    |       yes       |     yes      |
|                    | AES-EAX                               |   yes    |       yes       |     yes      |
|                    | KMS Envelope                          |   yes    |     **no**      |    **no**    |
|                    | CHACHA20-POLY1305                     |   yes    |     **no**      |    **no**    |
|                    | XCHACHA20-POLY1305                    |   yes    |       yes       |     yes      |
| Streaming AEAD     | AES-GCM-HKDF-STREAMING                |   yes    |     **no**      |    **no**    |
|                    | AES-CTR-HMAC-STREAMING                |   yes    |     **no**      |    **no**    |
| Deterministic AEAD | AES-SIV                               |   yes    |       yes       |     yes      |
| MAC                | HMAC-SHA2                             |   yes    |       yes       |     yes      |
|                    | AES-CMAC                              |   yes    |       yes       |     yes      |
| PRF                | HKDF-SHA2                             |   yes    |     **no**      |    **no**    |
|                    | HMAC-SHA2                             |   yes    |     **no**      |    **no**    |
|                    | AES-CMAC                              |   yes    |     **no**      |    **no**    |
| Digital Signatures | ECDSA over NIST curves                |   yes    |       yes       |     yes      |
|                    | Ed25519                               |   yes    |       yes       |     yes      |
|                    | RSA-SSA-PKCS1                         |   yes    |       yes       |     yes      |
|                    | RSA-SSA-PSS                           |   yes    |       yes       |     yes      |
| Hybrid Encryption  | HPKE                                  |   yes    |     **no**      |    **no**    |
|                    | ECIES with AEAD and HKDF              |   yes    |       yes       |     yes      |
|                    | ECIES with DeterministicAEAD and HKDF |   yes    |     **no**      |    **no**    |

## Compatibility

| **Version** | **Kotlin** | **Tink-android** | **Tink-ObjC** |
| ----------- | :--------: | :--------------: | :-----------: |
| 0.0.1       |   1.8.20   |      1.7.0       |     1.7.0     |

> **Warning**</br>
> Tink-ObjC 1.7.0 has not been released to CocoaPods yet.</br> > https://github.com/google/tink/issues/583</br> > https://github.com/google/tink/issues/641</br>
> so, you need to build Tink-ObjC 1.7.0 by yourself.</br>
> you can use my fork [RyuNen344/tink](https://github.com/RyuNen344/tink), and [Makefile](Makefile) can be used as a reference about how to build Tink-ObjC 1.7.0.</br>

## Installation

add the following to your `settings.gradle` and `build.gradle` file:

```kotlin:settings.gradle
repositories {
    mavenCentral()
}
```

```kotlin:build.gradle
kotlin {
    // if you want to use without CocoaPods, you need to link with your Tink.framework like below.
    iosX64 {
        binaries {
            framework {
                linkerOpts(
                    listOf(
                        "-framework",
                        "Tink",
                        "-Fpath/to/Tink.framework",
                        "-rpath",
                        "path/to/Tink.framework",
                        "-ObjC",
                    )
                )
            }
        }
    }

    commonMain {
        dependencies {
            implementation "io.github.ryunen344.tink:tink:$tink_kmm_version"
        }
    }
}
```

## Usage

you can use Tink-KMM like java Tink.

### Initialization

```kotlin
import io.github.ryunen344.tink.aead.AeadConfig
import io.github.ryunen344.tink.aead.register
import io.github.ryunen344.tink.daead.DeterministicAeadConfig
import io.github.ryunen344.tink.daead.register
import io.github.ryunen344.tink.hybrid.HybridConfig
import io.github.ryunen344.tink.hybrid.register
import io.github.ryunen344.tink.mac.MacConfig
import io.github.ryunen344.tink.mac.register
import io.github.ryunen344.tink.signature.SignatureConfig
import io.github.ryunen344.tink.signature.register

AeadConfig.register()
DeterministicAeadConfig.register()
HybridConfig.register()
MacConfig.register()
SignatureConfig.register()
```

### Generate new keys and keysets

```kotlin
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew

KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_GCM.template())
```

> **Note**
> Defined available key templates in [KeyTemplateSet](tink/src/commonMain/kotlin/io/github/ryunen344/tink/KeyTemplateSet.kt)

### Serialize and Deserialize

### Obtaining and using primitives

#### AEAD

```kotlin:Aead.kt
import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive

// 1. Generate the key material.
val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_GCM.template())

// 2. Get the primitive.
val aead = handle.getPrimitive(Aead::class)

// 3. Use the primitive to encrypt a plaintext,
val ciphertext = aead.encrypt(plaintext, associatedData)

// ... or to decrypt a ciphertext.
val decrypted = aead.decrypt(ciphertext, associatedData)
```

#### DeterministicAEAD

```kotlin:DeterministicAead.kt
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive

// 1. Generate the key material.
val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_GCM.template())

// 2. Get the primitive.
val daead = handle.getPrimitive(DeterministicAead::class)

// 3. Use the primitive to encrypt a plaintext,
val ciphertext = daead.encrypt(plaintext, associatedData)

// ... or to decrypt a ciphertext.
val decrypted = daead.decrypt(ciphertext, associatedData)
```

#### HybridAEAD

```kotlin:HybridAead.kt
import io.github.ryunen344.tink.hybrid.HybridEncrypt
import io.github.ryunen344.tink.hybrid.HybridDecrypt
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.publicKeysetHandle

// 1. Generate the key material.
val privateKeysetHandle = KeysetHandleGenerator.generateNew(KeyTemplateSet.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM.template())
val publicKeysetHandle = privateKeysetHandle.publicKeysetHandle()

// 2. Get the primitives.
val hybridEncrypt = publicKeysetHandle.getPrimitive(HybridEncrypt::class)
val hybridDecrypt = privateKeysetHandle.getPrimitive(HybridDecrypt::class)

// 3. Use the primitives to encrypt and decrypt.
val ciphertext = hybridEncrypt.encrypt(plaintext, contextInfo)
val decrypted = hybridDecrypt.decrypt(ciphertext, contextInfo)
```

#### MAC

```kotlin:Mac.kt
import io.github.ryunen344.tink.mac.Mac
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive

// 1. Generate the key material.
val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.HMAC_SHA256_128BITTAG.template())

// 2. Get the primitive.
val mac = handle.getPrimitive(Mac::class)

// 3. Use the primitive to compute a tag.
val tag = mac.computeMac(plaintext)

// ... or to verify a tag.
mac.verifyMac(tag, plaintext)
```

#### Signature

```kotlin:Signature.kt
import io.github.ryunen344.tink.signature.PublicKeySign
import io.github.ryunen344.tink.signature.PublicKeyVerify
import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.publicKeysetHandle

// 1. Generate the key material.
val privateHandle = KeysetHandleGenerator.generateNew(KeyTemplateSet.ECDSA_P256.template())
val publicHandle = privateHandle.publicKeysetHandle()

// 2. Get the primitive.
val signer = privateHandle.getPrimitive(PublicKeySign::class)
val verifier = publicHandle.getPrimitive(PublicKeyVerify::class)

// 3. Use the primitive to sign a message.
val signature = signer.sign(message)

// ... or to verify a signature.
verifier.verify(signature, message)
```

#### Swift(Optional)

This library also supports be used in Swift directory.

```swift
try! AeadConfig.companion.register()
let template = try! KeyTemplateSet.aes256Gcm.template()
let handle = try! KeysetHandleGenerator.companion.generateNew(keyTemplate: template)
let aead = try! KeysetHandleKt.getPrimitive(handle, kClass: TinkPrimitiveKt.aead) as! Aead
let ciphertext = try! aead.encrypt(plaintext, with: associatedData)
let decrypted = try! aead.decrypt(ciphertext, with: associatedData)
```
