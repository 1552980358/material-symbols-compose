package me.ks.chan.material.symbols.annotation

/**
 * Defining the abstract class or interface is required to be implemented as material symbol icon.
 * If a class is applied, both the class and icon customization property should be all marked as `abstract`.
 * Warned that the icon customization property should be grammarly abstract,
 * i.e., no implementation should be presented because the implementation will be cleared after KSP processing.
 * Added that the icon customization property should be annotated with [me.ks.chan.material.symbols.annotation.Style]
 * with setting one of the enum value of [me.ks.chan.material.symbols.annotation.MaterialSymbolStyle].
 * And the other customizations are optional, i.e.,
 *   1. [me.ks.chan.material.symbols.annotation.Weight],
 *   2. [me.ks.chan.material.symbols.annotation.Grade]
 *   3. [me.ks.chan.material.symbols.annotation.Filled]
 *   4. [me.ks.chan.material.symbols.annotation.OpticalSize]
 *
 * The class/interface name will be converted into name of icon in Material Symbols,
 * if parameter [MaterialSymbol.name] is not specified or leaves blank.
 * The classname / interface name should be in `PascalCase` (or `UpperCamelcase`) so that it can be converted to `snack_case`
 * that is exactly used in [google/material-design-icon](https://github.com/google/material-design-icons) repository correctly.
 * For example, classname `CheckCircle` will be converted into `check_circle` correctly.
 * If you want a custom classname, you can specify the exactly icon name in [google/material-design-icon](https://github.com/google/material-design-icons)
 * by adding string parameter to annotation [MaterialSymbol]
 *
 * ```kotlin
 * import androidx.compose.ui.graphics.vector.ImageVector
 * import me.ks.chan.material.symbols.annotation.Filled
 * import me.ks.chan.material.symbols.annotation.MaterialSymbol
 * import me.ks.chan.material.symbols.annotation.Style
 * import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle
 *
 * // Interface example
 * @MaterialSymbol
 * interface ArrowRight {
 *     @Style(MaterialSymbolStyle.Rounded)
 *     val rounded: ImageVector
 *
 *     @Style(MaterialSymbolStyle.Rounded)
 *     @Filled
 *     val filled: ImageVector
 * }
 *
 * // Abstract class example
 * @MaterialSymbol(
 *     /* Optional parameter name specification */
 *     name = "check_circle"
 * )
 * abstract class ValidCheck {
 *     @Style(MaterialSymbolStyle.Rounded)
 *     abstract val rounded: ImageVector
 *
 *     @Style(MaterialSymbolStyle.Rounded)
 *     @Filled
 *     abstract val filled: ImageVector
 * }
 * ```
 **/
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class MaterialSymbol(
    val name: String = ""
)