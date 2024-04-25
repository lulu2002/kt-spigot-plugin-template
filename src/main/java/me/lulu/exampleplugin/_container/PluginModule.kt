package me.lulu.exampleplugin._container

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import org.bukkit.plugin.Plugin

class PluginModule(private val plugin: Plugin) : AbstractModule() {

    @Provides
    @Singleton
    fun plugin(): Plugin = plugin

}