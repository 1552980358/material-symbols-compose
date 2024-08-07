package me.ks.chan.material.symbols.ksp.ext

private val CamelcaseRegex by lazy { "(?<=[a-zA-Z])[A-Z]".toRegex() }
private val SnackCaseRegex by lazy { "_[a-zA-Z]".toRegex() }

val String.asSnackCase: String
    get() = replace(CamelcaseRegex) { "_${it.value}" }.lowercase()

val String.asPascalCase: String
    get() = replace(SnackCaseRegex) { it.value.substring(1).uppercase() }
        .replaceFirstChar(Char::uppercaseChar)