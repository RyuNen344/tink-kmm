package io.github.ryunen344.tink.util

import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.hybrid.HybridDecrypt
import io.github.ryunen344.tink.hybrid.HybridEncrypt
import io.github.ryunen344.tink.mac.Mac
import io.github.ryunen344.tink.signature.PublicKeySign
import io.github.ryunen344.tink.signature.PublicKeyVerify
import kotlin.reflect.KClass

val Aead.kclass: KClass<Aead>
    get() = Aead::class

val DeterministicAead.kclass: KClass<DeterministicAead>
    get() = DeterministicAead::class

val HybridDecrypt.kclass: KClass<HybridDecrypt>
    get() = HybridDecrypt::class

val HybridEncrypt.kclass: KClass<HybridEncrypt>
    get() = HybridEncrypt::class

val Mac.kclass: KClass<Mac>
    get() = Mac::class

val PublicKeySign.kclass: KClass<PublicKeySign>
    get() = PublicKeySign::class

val PublicKeyVerify.kclass: KClass<PublicKeyVerify>
    get() = PublicKeyVerify::class
