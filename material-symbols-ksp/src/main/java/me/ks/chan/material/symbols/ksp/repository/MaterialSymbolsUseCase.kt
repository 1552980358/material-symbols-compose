package me.ks.chan.material.symbols.ksp.repository

import com.google.devtools.ksp.processing.KSPLogger
import me.ks.chan.material.symbols.ksp.annotation.MaterialSymbolIcon
import me.ks.chan.material.symbols.ksp.ext.asPascalCase
import okhttp3.OkHttpClient

class MaterialSymbolsUseCase(
    icon: String,
    materialSymbolIcon: MaterialSymbolIcon,
    kspLogger: KSPLogger,
) {

    private val materialSymbolsRepository by lazy {
        GoogleMaterialSymbolsRepository(icon, materialSymbolIcon)
    }

    init {
        val iconName = icon.asPascalCase

        kspLogger.apply {
            info(
                "Icon=${iconName}: " +
                    "Style=${materialSymbolIcon.style.name}, " +
                    "Weight=${materialSymbolIcon.weight.name}, " +
                    "Grade=${materialSymbolIcon.grade.name}, " +
                    "Filled=${materialSymbolIcon.filled}"
            )
            info("$iconName URL: ${materialSymbolsRepository.repositoryUrl}")
        }
    }

    fun fetch(okHttpClient: OkHttpClient): List<PathBuilderCommand> {
        val vectorDrawable = materialSymbolsRepository.fetch(okHttpClient)
        val pathCommandList = vectorDrawable processWith VectorDrawableRepository
        val pathBuilderCommandList = pathCommandList processWith PathBuilderRepository

        return pathBuilderCommandList
    }

}