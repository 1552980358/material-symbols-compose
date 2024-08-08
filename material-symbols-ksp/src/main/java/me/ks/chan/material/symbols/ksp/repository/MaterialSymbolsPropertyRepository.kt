package me.ks.chan.material.symbols.ksp.repository

import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import me.ks.chan.material.symbols.annotation.MaterialSymbolOpticalSize
import me.ks.chan.material.symbols.ksp.annotation.MaterialSymbolIcon
import me.ks.chan.material.symbols.ksp.ext.ComposeUiVectorGraphics
import me.ks.chan.material.symbols.ksp.ext.MaterialSymbols

class MaterialSymbolsPropertyRepository(
    classname: String,
    private val propertyDeclaration: KSPropertyDeclaration,
    private val materialSymbolIcon: MaterialSymbolIcon,
): ProcessRepository<List<PathBuilderCommand>, PropertySpec> {

    private val iconName = buildString {
        append(classname)
        if (materialSymbolIcon.filled) {
            append("-Filled")
        }
        append(".Weight-")
        append(materialSymbolIcon.weight.name)
        append(".Grade-")
        append(materialSymbolIcon.grade.name)
        append(".OpticalSize-")
        append(materialSymbolIcon.opticalSize.name)
    }

    private val opticalSize = materialSymbolIcon.opticalSizeInt

    override fun process(unprocessed: List<PathBuilderCommand>): PropertySpec {
        return PropertySpec.builder(name = propertyDeclaration.simpleName.asString(), type = ImageVectorType)
            .addOriginatingKSFile(propertyDeclaration.containingFile!!)
            .addModifiers(KModifier.OVERRIDE)
            .delegate(
                CodeBlock.builder()
                    .beginControlFlow(
                        controlFlow = "${MaterialSymbols.MaterialSymbol.asMethod}(name = %S, size = %L)",
                        args = arrayOf(iconName, opticalSize)
                    )
                    .addPathBuilderCommands(unprocessed)
                    .endControlFlow()
                    .build()
            )
            .build()
    }

}

private inline val ImageVectorType: TypeName
    get() = ComposeUiVectorGraphics.ImageVector.classClassName

private inline val MaterialSymbolIcon.opticalSizeInt: Int
    get() = when (opticalSize) {
        MaterialSymbolOpticalSize.Small -> 20
        MaterialSymbolOpticalSize.Regular -> 24
        MaterialSymbolOpticalSize.Large -> 40
        MaterialSymbolOpticalSize.Larger -> 48
    }

private fun CodeBlock.Builder.addPathBuilderCommands(
    pathBuilderCommandList: List<PathBuilderCommand>
): CodeBlock.Builder = apply {
    pathBuilderCommandList.forEach {
        addStatement(it.asPathBuildCommandStr, *it.floatParameters)
    }
}

private inline val PathBuilderCommand.asPathBuildCommandStr
    get() = "${command}(${List(floatParameters.size) { "%LF" }.joinToString(separator = ", ")})"