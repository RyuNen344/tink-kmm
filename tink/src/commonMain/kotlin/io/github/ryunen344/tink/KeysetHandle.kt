package io.github.ryunen344.tink

import io.github.ryunen344.tink.exception.GeneralSecurityException
import kotlin.reflect.KClass

expect class KeysetHandle

@Throws(GeneralSecurityException::class)
expect fun generateNew(keyTemplate: KeyTemplate): KeysetHandle

@Throws(GeneralSecurityException::class)
expect fun <P : TinkPrimitive> KeysetHandle.getPrimitive(kClass: KClass<P>): P

@Throws(GeneralSecurityException::class)
expect fun KeysetHandle.publicKeysetHandle(): KeysetHandle
