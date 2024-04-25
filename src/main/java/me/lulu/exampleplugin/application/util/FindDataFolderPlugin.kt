package me.lulu.exampleplugin.application.util

import org.bukkit.plugin.Plugin
import java.nio.file.Path

class FindDataFolderPlugin(private val plugin: Plugin) : FindDataFolder {

    override fun getDataFolder(): Path {
        return plugin.dataFolder.toPath()
    }

}