package me.ks.chan.material.symbols.example.icon

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.material.symbols.MaterialSymbols
import me.ks.chan.material.symbols.annotation.MaterialSymbol
import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle
import me.ks.chan.material.symbols.annotation.Style

/** 1. Annotate @MaterialSymbol **/
@MaterialSymbol
/** 2. Define `abstract class` **/
abstract class Settings {

    /** 3. Annotate @Style with parameter filled with [MaterialSymbolStyle] **/
    @Style(MaterialSymbolStyle.Rounded)
    /** 4. Define `abstract` property **/
    protected /** 5. You can specify protected modifier in abstract class **/
    abstract val rounded: ImageVector /** 6. Define property type as [ImageVector] **/

    /**
     * CAUTION:
     * Other property or should not be abstract (without implementation)
     * It is strongly recommended not to add any members other than defining [Style] property
     **/
    @Composable
    fun DefaultIcon(contentDescription: String? = null) {
        Icon(
            imageVector = rounded,
            contentDescription = contentDescription
        )
    }

}

/** Let's preview **/
@Composable
@Preview
private fun Preview() {
    Column {
        IconButton(onClick = { /*TODO*/ }) {
            MaterialSymbols.Settings.DefaultIcon()
        }
    }
}