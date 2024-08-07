package me.ks.chan.material.symbols.ksp.repository

import com.google.devtools.ksp.processing.KSPLogger
import me.ks.chan.material.symbols.annotation.MaterialSymbolGrade
import me.ks.chan.material.symbols.annotation.MaterialSymbolWeight
import me.ks.chan.material.symbols.ksp.annotation.MaterialSymbolIcon
import me.ks.chan.material.symbols.ksp.annotation.filledUrlOption
import me.ks.chan.material.symbols.ksp.annotation.gradeUrlOption
import me.ks.chan.material.symbols.ksp.annotation.opticalSizeInt
import me.ks.chan.material.symbols.ksp.annotation.styleUrlOption
import me.ks.chan.material.symbols.ksp.annotation.weightUrlOption
import me.ks.chan.material.symbols.ksp.ext.asPascalCase
import okhttp3.OkHttpClient
import okhttp3.Request

class GoogleMaterialSymbolsRepository(
    kspLogger: KSPLogger,
    icon: String,
    materialSymbolIcon: MaterialSymbolIcon,
) {

    @Suppress("SpellCheckingInspection")
    private val repositoryUrl by lazy {
        "https://github.com/google/material-design-icons/blob/master/symbols/android/" +
            icon + "/materialsymbols" + materialSymbolIcon.styleUrlOption + "/" +
            icon + materialSymbolIcon.customizationOption + "_" +
            "${materialSymbolIcon.opticalSizeInt}px.xml"
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
            info("$iconName URL: $repositoryUrl")
        }
    }

    private val repositoryRequest: Request
        get() = Request.Builder()
            .url(repositoryUrl)
            .get()
            .build()

    fun fetch(okHttpClient: OkHttpClient): String =
        okHttpClient.newCall(repositoryRequest)
            .execute()
            .body!!
            .string()

}

private val MaterialSymbolIcon.customizationOption: String
    get() = when {
        weight == MaterialSymbolWeight.Regular &&
            grade == MaterialSymbolGrade.Regular &&
            !filled -> { "" }
        else -> { "_${weightUrlOption}${gradeUrlOption}${filledUrlOption}" }
    }