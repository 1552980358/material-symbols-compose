package me.ks.chan.material.symbols.ksp.repository

import kotlin.text.MatchResult.Destructured

object VectorDrawableRepository {
    fun process(vectorDrawable: String): List<String> {
            return vectorDrawable.lineSequence()
                .pathCommand
                .asPathCommandList
    }
}

@Suppress("SpellCheckingInspection")
private val PathDataAttributeRegex by lazy("^ {6}android:pathData=\"([MQLZ\\d ,.]+)\"/>"::toRegex)
@Suppress("SpellCheckingInspection")
private val VectorPathCommandActionRegex by lazy("(?=[MQLZ])"::toRegex)

private inline val Sequence<String>.pathCommand: String
    get() = firstNotNullOf { PathDataAttributeRegex.find(it)?.destructured }
        .let(Destructured::component1)

private inline val String.asPathCommandList: List<String>
    get() = split(VectorPathCommandActionRegex).filter(String::isNotBlank)