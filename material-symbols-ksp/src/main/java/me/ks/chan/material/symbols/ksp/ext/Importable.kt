package me.ks.chan.material.symbols.ksp.ext

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec

internal sealed interface Importable {
    val pkg: String

    val asClass: String
        get() = this::class.java.simpleName

    val asMethod: String
        get() = asClass.replaceFirstChar(Char::lowercaseChar)

    val classClassName: ClassName
        get() = ClassName(pkg, asClass)
    val methodClassName: ClassName
        get() = ClassName(pkg, asMethod)
}

internal data object MaterialSymbols: Importable {
    override val pkg: String
        get() = "me.ks.chan.material.symbols"
    override val asClass: String
        get() = throw IllegalAccessException()
    override val asMethod: String
        get() = "materialSymbol"
    val filename: String
        get() = this::class.java.simpleName
}

internal sealed interface ComposeUi: Importable {
    override val pkg: String
        get() = "androidx.compose.ui"
}

internal sealed interface ComposeUiGraphics: ComposeUi {
    override val pkg: String
        get() = "${super.pkg}.graphics"

    data object Color: ComposeUiGraphics
    data object SolidColor: ComposeUiGraphics
    data object StrokeJoin: ComposeUiGraphics
}

internal sealed interface ComposeUiVectorGraphics: ComposeUiGraphics {
    override val pkg: String
        get() = "${super.pkg}.vector"

    data object ImageVector: ComposeUiVectorGraphics
    data object PathBuilder: ComposeUiVectorGraphics
    data object Path: ComposeUiVectorGraphics
}

internal sealed interface ComposeUiUnit: ComposeUi {
    override val pkg: String
        get() = "${super.pkg}.unit"

    data object Dp: ComposeUiUnit
}

internal fun FileSpec.Builder.importClass(importable: Importable) = apply {
    addImport(importable.pkg, importable.asClass)
}

internal fun FileSpec.Builder.importMethod(importable: Importable) = apply {
    addImport(importable.pkg, importable.asMethod)
}
