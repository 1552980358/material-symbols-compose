package me.ks.chan.material.symbols.ksp.visitor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.ksp.toClassName
import me.ks.chan.material.symbols.annotation.Filled
import me.ks.chan.material.symbols.annotation.Grade
import me.ks.chan.material.symbols.annotation.MaterialSymbol
import me.ks.chan.material.symbols.annotation.OpticalSize
import me.ks.chan.material.symbols.annotation.Style
import me.ks.chan.material.symbols.annotation.Weight
import me.ks.chan.material.symbols.ksp.annotation.MaterialSymbolIcon
import me.ks.chan.material.symbols.ksp.coder.MaterialSymbolCoder
import me.ks.chan.material.symbols.ksp.coder.starts
import me.ks.chan.material.symbols.ksp.ext.annotation
import me.ks.chan.material.symbols.ksp.ext.annotationOrNull
import me.ks.chan.material.symbols.ksp.ext.asSnackCase
import me.ks.chan.material.symbols.ksp.repository.MaterialSymbolsPropertyRepository
import me.ks.chan.material.symbols.ksp.repository.MaterialSymbolsUseCase
import me.ks.chan.material.symbols.ksp.repository.processWith
import okhttp3.OkHttpClient

class MaterialSymbolClassVisitor(
    private val kspLogger: KSPLogger,
    private val codeGenerator: CodeGenerator,
    private val okHttpClient: OkHttpClient,
): KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        val className = classDeclaration.toClassName()

        val icon = classDeclaration.annotation(MaterialSymbol::name)
            .takeIf(String::isNotBlank) ?: className.simpleName.asSnackCase

        @OptIn(KspExperimental::class)
        val propertyDeclarationList = classDeclaration.getDeclaredProperties()
            .filter { it.isAnnotationPresent(Style::class) && it.isAbstract() }
            .toList()

        val propertySpecList = propertyDeclarationList.map { propertyDeclaration ->
            val materialSymbolIcon = MaterialSymbolIcon(propertyDeclaration)
            val pathBuilderCommandList = MaterialSymbolsUseCase(icon, materialSymbolIcon, kspLogger)
                .fetch(okHttpClient)

            pathBuilderCommandList processWith MaterialSymbolsPropertyRepository(
                propertyDeclaration, materialSymbolIcon
            )
        }

        codeGenerator starts MaterialSymbolCoder(classDeclaration, propertySpecList)
    }
}

private fun MaterialSymbolIcon(propertyDeclaration: KSPropertyDeclaration): MaterialSymbolIcon {
    return MaterialSymbolIcon(
        style = propertyDeclaration.annotation(Style::value),
        weight = propertyDeclaration.annotationOrNull(Weight::value),
        grade = propertyDeclaration.annotationOrNull(Grade::value),
        filled = propertyDeclaration.annotationOrNull<Filled>() != null,
        opticalSize = propertyDeclaration.annotationOrNull(OpticalSize::value),
    )
}