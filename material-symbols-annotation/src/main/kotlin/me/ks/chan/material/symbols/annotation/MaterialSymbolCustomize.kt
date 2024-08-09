package me.ks.chan.material.symbols.annotation

/**
 * The [Style] specification for [MaterialSymbol].
 * It marks a abstract field or property as implementation holder for icon,
 * and the return type of field or property should be [`androidx.compose.ui.graphics.vector.ImageVector`](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector).
 * So must be declared to a abstract property or field inside [MaterialSymbol]:
 * ```kotlin
 * import androidx.compose.ui.graphics.vector.ImageVector
 * import me.ks.chan.material.symbols.annotation.MaterialSymbol
 * import me.ks.chan.material.symbols.annotation.Style
 * import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle
 *
 * @MaterialSymbol
 * interface Home {
 *     @Style(MaterialSymbolStyle.Rounded)
 *     val rounded: ImageVector
 * }
 * ```
 *
 * Warning: Not declaring [Style] to abstract property or field inside [MaterialSymbol]
 * will cause to compilation error of abstract member not implemented after code generation.
 *
 * Refers to [Icon & Material Symbol styles](https://m3.material.io/styles/icons/applying-icons#06499df4-5998-4724-bea1-8d87327fde70),
 * Material Symbols allows customizing icon style:
 * [MaterialSymbolStyle.Outlined], [MaterialSymbolStyle.Rounded], [MaterialSymbolStyle.Sharp]
 **/
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class Style(val value: MaterialSymbolStyle)

/**
 * This is enum class for [Style.value]
 **/
enum class MaterialSymbolStyle { Outlined, Rounded, Sharp }

/**
 * The [Weight] customization for [MaterialSymbol].
 *
 * Refers to [Weight](https://m3.material.io/styles/icons/applying-icons#d7f45762-67ac-473d-95b0-9214c791e242),
 * Weight defines the symbol’s stroke weight, with a range of weights between thin (100) and bold (700).
 * Weight can also affect the overall size of the symbol.
 * Default weight is set as 400 ([MaterialSymbolWeight.Regular]), but weight can be customized as
 * 100 ([MaterialSymbolWeight.Thinnest])
 * 200 ([MaterialSymbolWeight.Thin])
 * 300 ([MaterialSymbolWeight.Thinner])
 * 400 ([MaterialSymbolWeight.Regular])
 * 500 ([MaterialSymbolWeight.Bolder])
 * 600 ([MaterialSymbolWeight.Bold])
 * 700 ([MaterialSymbolWeight.Boldest])
 **/
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class Weight(val value: MaterialSymbolWeight)

/**
 * This is the enum class for [Weight.value]
 **/
enum class MaterialSymbolWeight { Thinnest, Thin, Thinner, Regular, Bolder, Bold, Boldest }

/**
 * The [Grade] customization for [MaterialSymbol].
 *
 * Refers to [Grade](https://m3.material.io/styles/icons/applying-icons#3ad55207-1cb0-43af-8092-fad2762f69f7),
 * Grade can also compensate for visual bleed, which is when images can look bigger or smaller depending on the color contrast.
 * To match the apparent icon size, the default grade for a dark icon on a light background is 0, and -25 for a light icon on a dark background.
 *
 * You can use grade for different needs:
 * Low emphasis (25 grade, [MaterialSymbolGrade.Low]): To reduce glare for a light symbol on a dark background, use a low grade.
 * High emphasis (200 grade, [MaterialSymbolGrade.High]): To highlight a symbol, increase the positive grade.
 **/
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class Grade(val value: MaterialSymbolGrade)

/**
 * This is the enum class for [Grade.value]
 **/
enum class MaterialSymbolGrade { Low, Regular, High }

/**
 * The [Filled] customization for [MaterialSymbol].
 *
 * Set icon to be filled when annotated. If no [Filled] is annotated, the icon will be in outline.
 **/
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class Filled

/**
 * The [OpticalSize] customization for [MaterialSymbol].
 *
 * Refers to [Optical sizes](https://m3.material.io/styles/icons/applying-icons#b41cbc01-9b49-4a44-a525-d153d1ea1425),
 * For the image to look the same at different sizes, the stroke weight (thickness) changes as the icon size scales.
 * Optical size offers a way to automatically adjust the stroke weight when you increase or decrease the symbol size.
 *
 * By default, [OpticalSize] is 24([MaterialSymbolOpticalSize.Regular]), but optical size can be customized:
 * 20 ([MaterialSymbolOpticalSize.Small])
 * 24 ([MaterialSymbolOpticalSize.Regular])
 * 40 ([MaterialSymbolOpticalSize.Large])
 * 48 ([MaterialSymbolOpticalSize.Larger])
 **/
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class OpticalSize(val value: MaterialSymbolOpticalSize)

/**
 * This is the enum class for [OpticalSize.value]
 **/
enum class MaterialSymbolOpticalSize { Small, Regular, Large, Larger }
