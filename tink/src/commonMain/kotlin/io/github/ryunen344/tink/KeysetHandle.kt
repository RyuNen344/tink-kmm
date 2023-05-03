package io.github.ryunen344.tink

import io.github.ryunen344.tink.exception.GeneralSecurityException
import io.github.ryunen344.tink.exception.IOException
import kotlin.reflect.KClass

expect class KeysetHandle

@Throws(GeneralSecurityException::class, IOException::class)
expect fun KeysetHandle.writeNoSecret(writer: KeysetWriter)

@Throws(GeneralSecurityException::class, IOException::class)
expect fun KeysetHandle.writeCleartext(writer: KeysetWriter)

@Throws(GeneralSecurityException::class)
expect fun <P : TinkPrimitive> KeysetHandle.getPrimitive(kClass: KClass<P>): P

@Throws(GeneralSecurityException::class)
expect fun KeysetHandle.publicKeysetHandle(): KeysetHandle
