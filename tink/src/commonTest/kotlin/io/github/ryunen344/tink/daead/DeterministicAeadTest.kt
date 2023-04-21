package io.github.ryunen344.tink.daead

import io.github.ryunen344.tink.KeyTemplateSet
import io.github.ryunen344.tink.KeysetHandleGenerator
import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.generateNew
import io.github.ryunen344.tink.getPrimitive
import io.github.ryunen344.tink.template
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class DeterministicAeadTest {

    @BeforeTest
    fun setup() {
        DeterministicAeadConfig.register()
    }

    @Test
    fun test_encrypt_then_success() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)
        val decrypted = daead.decryptDeterministically(ciphertext, associatedData)

        assertContentEquals(plaintext, decrypted)
        assertContentEquals(ciphertext, daead.encryptDeterministically(plaintext, associatedData))
    }

    @Test
    fun test_encrypt_given_empty_then_success() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val empty = "".encodeToByteArray()

        assertContentEquals(
            empty,
            daead.decryptDeterministically(daead.encryptDeterministically(empty, associatedData), associatedData)
        )
        assertContentEquals(
            plaintext,
            daead.decryptDeterministically(daead.encryptDeterministically(plaintext, empty), empty)
        )
    }

    @Test
    fun test_decrypt_given_invalid_associate_data_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            daead.decryptDeterministically(ciphertext, invalid)
        }
    }

    @Test
    fun test_decrypt_given_invalid_data_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val associatedData = "associatedData".encodeToByteArray()
        val invalid = "invalid".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            daead.decryptDeterministically(invalid, associatedData)
        }
    }

    @Test
    fun test_decrypt_given_empty_data_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val associatedData = "associatedData".encodeToByteArray()
        val empty = "".encodeToByteArray()

        assertFailsWith<GeneralSecurityException> {
            daead.decryptDeterministically(empty, associatedData)
        }
    }

    @Test
    fun test_decrypt_given_other_daead_then_throws_error() {
        val handle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val daead = handle.getPrimitive(DeterministicAead::class)
        val plaintext = "plaintext".encodeToByteArray()
        val associatedData = "associatedData".encodeToByteArray()
        val ciphertext = daead.encryptDeterministically(plaintext, associatedData)

        val otherHandle = KeysetHandleGenerator.generateNew(KeyTemplateSet.AES256_SIV.template())
        val otherDaead = otherHandle.getPrimitive(DeterministicAead::class)

        assertFailsWith<GeneralSecurityException> {
            otherDaead.decryptDeterministically(ciphertext, associatedData)
        }
    }
}
