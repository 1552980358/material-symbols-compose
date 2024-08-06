package me.ks.chan.material.symbols.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import me.ks.chan.material.symbols.ksp.coder.MaterialSymbolsCoder
import me.ks.chan.material.symbols.ksp.coder.starts

class MaterialSymbolsSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val kspLogger: KSPLogger,
): SymbolProcessor {

    init {
        codeGenerator starts MaterialSymbolsCoder
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        TODO("Not yet implemented")
    }

}