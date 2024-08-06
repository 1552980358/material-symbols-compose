package me.ks.chan.material.symbols.ksp.annotation

import me.ks.chan.material.symbols.annotation.MaterialSymbolGrade
import me.ks.chan.material.symbols.annotation.MaterialSymbolOpticalSize
import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle
import me.ks.chan.material.symbols.annotation.MaterialSymbolWeight

data class MaterialSymbolIcon(
    val style: MaterialSymbolStyle,
    val weight: MaterialSymbolWeight,
    val grade: MaterialSymbolGrade,
    val filled: Boolean,
    val opticalSize: MaterialSymbolOpticalSize,
)

fun MaterialSymbolIcon(
    style: MaterialSymbolStyle,
    weight: MaterialSymbolWeight?,
    grade: MaterialSymbolGrade?,
    filled: Boolean,
    opticalSize: MaterialSymbolOpticalSize?,
): MaterialSymbolIcon {
    return MaterialSymbolIcon(
        style = style,
        weight = weight ?: MaterialSymbolWeight.Regular,
        grade = grade ?: MaterialSymbolGrade.Regular,
        filled = filled,
        opticalSize = opticalSize ?: MaterialSymbolOpticalSize.Regular,
    )
}
