package me.ks.chan.material.symbols.ksp.coder

import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.asClassName
import me.ks.chan.material.symbols.ksp.ext.ComposeUiGraphics
import me.ks.chan.material.symbols.ksp.ext.ComposeUiUnit
import me.ks.chan.material.symbols.ksp.ext.ComposeUiVectorGraphics
import me.ks.chan.material.symbols.ksp.ext.MaterialSymbols
import me.ks.chan.material.symbols.ksp.ext.importClass
import me.ks.chan.material.symbols.ksp.ext.importMethod

object MaterialSymbolsCoder: Coder {

    override val dependencies: Dependencies
        get() = Dependencies(aggregating = false)

    override val fileSpec: FileSpec
        get() = FileSpec.builder(MaterialSymbols.MaterialSymbol.classClassName)
            .addImports()
            .addType(MaterialSymbolsObject)
            .addFunction(MaterialSymbolLazyFun)
            .addProperty(ViewportSize.asProperty)
            .addFunction(MaterialSymbolBuilderFun)
            .build()

}

private fun FileSpec.Builder.addImports() = apply {
    importClass(ComposeUiGraphics.Color)
    importClass(ComposeUiGraphics.SolidColor)
    importClass(ComposeUiGraphics.StrokeJoin)

    importClass(ComposeUiVectorGraphics.ImageVector)
    importClass(ComposeUiVectorGraphics.PathBuilder)
    importMethod(ComposeUiVectorGraphics.Path)

    importClass(ComposeUiUnit.Dp)
    importMethod(ComposeUiUnit.Dp)
}

private inline val MaterialSymbolsObject: TypeSpec
    get() = TypeSpec.objectBuilder(MaterialSymbols.asClass)
        .build()

private data object ViewportSize {
    const val NAME = "ViewportSize"
    private const val VALUE = 960F
    private const val FORMAT = "%LF"

    inline val asProperty: PropertySpec
        get() = PropertySpec.builder(NAME, VALUE::class)
            .addModifiers(KModifier.PRIVATE)
            .initializer(FORMAT, VALUE)
            .build()
}

private inline val PathBuilderBlock: LambdaTypeName
    get() = LambdaTypeName.get(
        receiver = ComposeUiVectorGraphics.PathBuilder.classClassName,
        returnType = UNIT,
    )

private inline val MaterialSymbolLazyFun: FunSpec
    get() = FunSpec.builder(MaterialSymbols.MaterialSymbol.asMethod)
        .addParameter("name", String::class)
        .addParameter("size", Int::class)
        .addParameter("pathBuilder", PathBuilderBlock)
        .returns(
            Lazy::class.asClassName()
                .parameterizedBy(ComposeUiVectorGraphics.ImageVector.classClassName)
        )
        .addCode(
            CodeBlock.Builder()
                .beginControlFlow("return lazy")
                .addStatement("materialSymbol(name = name, size = size.dp, pathBuilder = pathBuilder)")
                .endControlFlow()
                .build()
        )
        .build()

private inline val MaterialSymbolBuilderFun: FunSpec
    get() = FunSpec.builder(MaterialSymbols.MaterialSymbol.asMethod)
        .addModifiers(KModifier.PRIVATE)
        .addParameter("name", String::class)
        .addParameter("size", ComposeUiUnit.Dp.classClassName)
        .addParameter("pathBuilder", PathBuilderBlock)
        .returns(ComposeUiVectorGraphics.ImageVector.classClassName)
        .addCode(
            CodeBlock.Builder()
                .add("return ImageVector")
                .indent()
                .add(
                    """
                    |.Builder(
                    |    name = name,
                    |    defaultWidth = size,
                    |    defaultHeight = size,
                    |    viewportWidth = ${ViewportSize.NAME},
                    |    viewportHeight = ${ViewportSize.NAME},
                    |)
                    """.trimMargin()
                )
                .add(
                    """
                    |.path(
                    |    fill = SolidColor(Color.Black),
                    |    pathBuilder = pathBuilder,
                    |    strokeLineJoin = StrokeJoin.Bevel,
                    |    strokeLineMiter = 1F,
                    |    strokeLineWidth = 1F,
                    |)
                    """.trimMargin()
                )
                .add(".build()")
                .unindent()
                .build()
        )
        .build()
