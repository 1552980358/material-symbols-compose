package me.ks.chan.material.symbols.ksp.annotation

import me.ks.chan.material.symbols.annotation.MaterialSymbolGrade
import me.ks.chan.material.symbols.annotation.MaterialSymbolOpticalSize
import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle
import me.ks.chan.material.symbols.annotation.MaterialSymbolWeight

inline val MaterialSymbolIcon.filledUrlOption: String
    get() = if (filled) "fill1" else ""

inline val MaterialSymbolIcon.styleUrlOption: String
    get() = when (style) {
        MaterialSymbolStyle.Outlined -> "outlined"
        MaterialSymbolStyle.Rounded -> "rounded"
        MaterialSymbolStyle.Sharp -> "sharp"
    }

inline val MaterialSymbolIcon.weightUrlOption: String
    get() = when (weight) {
        MaterialSymbolWeight.Thinnest -> "wght100"
        MaterialSymbolWeight.Thin -> "wght200"
        MaterialSymbolWeight.Thinner -> "wght300"
        MaterialSymbolWeight.Regular -> ""
        MaterialSymbolWeight.Bold -> "wght500"
        MaterialSymbolWeight.Bolder -> "wght600"
        MaterialSymbolWeight.Boldest -> "wght700"
    }

inline val MaterialSymbolIcon.gradeUrlOption: String
    get() = when (grade) {
        MaterialSymbolGrade.Low -> "gradN25"
        MaterialSymbolGrade.Regular -> ""
        MaterialSymbolGrade.High -> "grad200"
    }

inline val MaterialSymbolIcon.opticalSizeInt: Int
    get() = when (opticalSize) {
        MaterialSymbolOpticalSize.Small -> 20
        MaterialSymbolOpticalSize.Regular -> 24
        MaterialSymbolOpticalSize.Large -> 40
        MaterialSymbolOpticalSize.Larger -> 48
    }
