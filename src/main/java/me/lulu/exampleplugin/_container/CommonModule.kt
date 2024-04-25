package me.lulu.exampleplugin._container

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import me.lulu.exampleplugin.application.util.FindDataFolder
import me.lulu.exampleplugin.application.util.FindDataFolderPlugin
import me.lulu.exampleplugin.application.util.config.GetDefaultResourcePluginSource
import me.lulu.exampleplugin.application.util.config.MessageConfig
import me.lulu.exampleplugin.application.util.message.KeyedMessage
import me.lulu.exampleplugin.application.util.message.KeyedMessageFromConfig
import me.lulu.exampleplugin.application.util.message.Messenger
import me.lulu.exampleplugin.application.util.message.MessengerImpl
import org.bukkit.plugin.Plugin

class CommonModule : AbstractModule() {

    @Provides
    @Singleton
    fun messenger(keyedMessage: KeyedMessage): Messenger {
        return MessengerImpl(keyedMessage)
    }

    @Provides
    @Singleton
    fun keyedMessage(messageConfig: MessageConfig): KeyedMessage {
        return KeyedMessageFromConfig(messageConfig)
    }

    @Provides
    @Singleton
    fun messageConfig(findDataFolder: FindDataFolder, plugin: Plugin): MessageConfig {
        val path = findDataFolder.getDataFolder().resolve("messages.yml")
        return MessageConfig(
            path,
            GetDefaultResourcePluginSource(plugin, "messages.yml")
        );
    }

    @Provides
    @Singleton
    fun findDataFolder(plugin: Plugin): FindDataFolder {
        return FindDataFolderPlugin(plugin)
    }


}