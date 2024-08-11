package me.ks.chan.material.symbols.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import me.ks.chan.material.symbols.ksp.environment.okHttpClient

class MaterialSymbolsSymbolProcessorProvider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val kspLogger = environment.logger
        kspLogger.info("Kotlin: ${environment.apiVersion}")
        kspLogger.info("KotlinCompiler: ${environment.apiVersion}")
        kspLogger.info("KSP: ${environment.kspVersion}")
        kspLogger.info("Platforms: ${environment.platforms}")

        val okHttpClient = environment.okHttpClient(kspLogger)

        return MaterialSymbolsSymbolProcessor(
            codeGenerator = environment.codeGenerator,
            kspLogger = kspLogger,
            okHttpClient = okHttpClient,
        )
    }
}