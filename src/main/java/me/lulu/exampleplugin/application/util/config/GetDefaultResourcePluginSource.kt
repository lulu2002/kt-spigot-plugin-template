package me.lulu.exampleplugin.application.util.config

import org.bukkit.configuration.Configuration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.InputStreamReader

class GetDefaultResourcePluginSource(
    private val plugin: Plugin,
    private val resourcePath: String
) : GetDefaultResource {

    override fun getDefaultResource(): Configuration {
        return plugin.getResource(resourcePath)
            .let { InputStreamReader(it) }
            .let { YamlConfiguration.loadConfiguration(it) }
    }

}
