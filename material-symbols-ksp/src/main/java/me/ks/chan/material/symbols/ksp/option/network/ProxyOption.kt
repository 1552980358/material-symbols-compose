package me.ks.chan.material.symbols.ksp.option.network

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import me.ks.chan.material.symbols.ksp.option.DefaultedOption
import me.ks.chan.material.symbols.ksp.option.Option
import me.ks.chan.material.symbols.ksp.option.option

private const val Key = "materialSymbols.network.proxy"

/**
 * ksp { arg("materialSymbols.network.proxy.host", ...) }
 * To config the host for network proxy.
 * Once configured, the proxy is then enabled.
 */
private const val HostKey = "$Key.host"
/**
 * ksp { arg("materialSymbols.network.proxy.port", ...) }
 * To config the port for network proxy.
 * Optional option, the default value is 8080 if not configured.
 */
private const val PortKey = "$Key.port"

data object ProxyOption {

    inline val SymbolProcessorEnvironment.proxyHost: String?
        get() = option(Host)

    inline val SymbolProcessorEnvironment.proxyPort: Int
        get() = option(Port)

    data object Host: Option<String> {
        override val key: String
            get() = HostKey
        override fun String.convert(): String = this
    }

    data object Port: DefaultedOption<Int> {
        override val key: String
            get() = PortKey
        override fun String.convert(): Int = toInt()
        override val default: Int
            get() = 8080
    }

}