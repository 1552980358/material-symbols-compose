package me.ks.chan.material.symbols.ksp.option

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

interface Option<T> {
    val key: String
    fun String.convert(): T
}

interface DefaultedOption<T> : Option<T> {
    val default: T
}

fun <T> SymbolProcessorEnvironment.option(option: Option<T>): T? =
    with(option) { options[option.key]?.convert() }

fun <T> SymbolProcessorEnvironment.option(option: DefaultedOption<T>): T =
    with(option) { options[option.key]?.convert() ?: default }
