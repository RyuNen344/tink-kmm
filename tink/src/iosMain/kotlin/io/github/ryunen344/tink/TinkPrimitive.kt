package io.github.ryunen344.tink

import io.github.ryunen344.tink.aead.Aead
import io.github.ryunen344.tink.daead.DeterministicAead
import io.github.ryunen344.tink.hybrid.HybridDecrypt
import io.github.ryunen344.tink.hybrid.HybridEncrypt
import io.github.ryunen344.tink.mac.Mac
import io.github.ryunen344.tink.signature.PublicKeySign
import io.github.ryunen344.tink.signature.PublicKeyVerify
import kotlin.reflect.KClass

val aead: KClass<Aead>
    get() = Aead::class

val deterministicAead: KClass<DeterministicAead>
    get() = DeterministicAead::class

val hybridDecrypt: KClass<HybridDecrypt>
    get() = HybridDecrypt::class

val hybridEncrypt: KClass<HybridEncrypt>
    get() = HybridEncrypt::class

val mac: KClass<Mac>
    get() = Mac::class

val publicKeySign: KClass<PublicKeySign>
    get() = PublicKeySign::class

val publicKeyVerify: KClass<PublicKeyVerify>
    get() = PublicKeyVerify::class
