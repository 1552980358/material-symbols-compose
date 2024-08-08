package me.ks.chan.material.symbols.ksp.coder

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import me.ks.chan.material.symbols.ksp.ext.ComposeUiVectorGraphics
import me.ks.chan.material.symbols.ksp.ext.MaterialSymbols
import me.ks.chan.material.symbols.ksp.ext.importClass
import me.ks.chan.material.symbols.ksp.ext.importMethod

class MaterialSymbolIconCoder(
    private val classDeclaration: KSClassDeclaration,
    private val propertySpecList: List<PropertySpec>
): Coder {
    override val dependencies = Dependencies(aggregating = true, classDeclaration.containingFile!!)

    override val fileSpec: FileSpec
        get() {
            val supertype = classDeclaration.toClassName()
            val classname = supertype.simpleName + "Impl"

            return FileSpec.builder(supertype.packageName, classname)
                .addImports()
                .addType(
                    TypeSpec.objectBuilder(classname)
                        .supertype(classDeclaration, supertype)
                        .addProperties(propertySpecList)
                        .build()
                )
                .addProperty(
                    PropertySpec.builder(supertype.simpleName, supertype)
                        .receiver(MaterialSymbols.classClassName)
                        .getter(
                            FunSpec.getterBuilder()
                                .addStatement(
                                    "return %T", ClassName(supertype.packageName, classname)
                                )
                                .build()
                        )
                        .build()
                )
                .build()
        }

}

private fun FileSpec.Builder.addImports() = apply {
    importClass(ComposeUiVectorGraphics.ImageVector)
    importClass(MaterialSymbols)
    importMethod(MaterialSymbols.MaterialSymbol)
}

private fun TypeSpec.Builder.supertype(
    classDeclaration: KSClassDeclaration,
    className: ClassName,
    isInterface: Boolean = classDeclaration.classKind == ClassKind.INTERFACE,
): TypeSpec.Builder = when {
    isInterface -> { addSuperinterface(className) }
    else -> { superclass(className) }
}