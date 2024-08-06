package me.ks.chan.material.symbols.ksp.coder

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo

interface Coder {

    val fileSpec: FileSpec

    val dependencies: Dependencies
        get() = Dependencies(aggregating = false)

}

infix fun CodeGenerator.starts(coder: Coder) {
    coder.fileSpec
        .writeTo(codeGenerator = this, coder.dependencies)
}