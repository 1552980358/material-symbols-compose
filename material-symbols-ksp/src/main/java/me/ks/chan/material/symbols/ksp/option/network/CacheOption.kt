package me.ks.chan.material.symbols.ksp.option.network

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import me.ks.chan.material.symbols.ksp.option.DefaultedOption
import me.ks.chan.material.symbols.ksp.option.Option
import me.ks.chan.material.symbols.ksp.option.option

private const val Key = "materialSymbols.network.cache"

/**
 * ksp { arg("materialSymbols.network.cache.cacheDirectory", ...) }
 * To config the cache directory for material symbols repository.
 * Once configured, the cache is then enabled.
 **/
private const val CacheDirectoryKey = "$Key.cacheDirectory"
/**
 * ksp { arg("materialSymbols.network.cache.cacheSize", ...) }
 * To config the cache size for material symbols repository.
 * The default value is 10MiB.
 **/
private const val CacheSizeKey = "$Key.cacheSize"

data object CacheOption {

    val SymbolProcessorEnvironment.cacheDirectory: String?
        get() = option(CacheDirectory)

    inline val SymbolProcessorEnvironment.cacheSize: Long
        get() = option(CacheSize)

    data object CacheDirectory: Option<String> {

        override val key: String
            get() = CacheDirectoryKey

        override fun String.convert(): String = this

    }

    data object CacheSize: DefaultedOption<Long> {

        override val key: String
            get() = CacheSizeKey

        override fun String.convert(): Long =
            // If the value is not a number, return the default value
            intOrNull ?: default

        private inline val Char.multiply: Long
            get() = when (this) {
                'K' -> { 1024 }
                'M' -> { 1024 * 1024 }
                'G' -> { 1024 * 1024 * 1024 }
                else -> { 1 }
            }

        private val String.intOrNull: Long?
            get() {
                val last = last()
                if (last in '0'..'9') {
                    return toLongOrNull()
                }
                return dropLast(1)
                    .toLongOrNull()
                    ?.let { it * last.multiply }
            }

        override val default: Long
            get() = 10 * 1024 * 1024

    }

}