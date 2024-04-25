package me.lulu.exampleplugin.application.util.message

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

interface Messenger {

    fun send(player: CommandSender, messageKey: String, args: Map<String, Any> = emptyMap())

    fun send(players: Iterable<CommandSender>, messageKey: String, args: Map<String, Any> = emptyMap())

    fun broadcast(messageKey: String, args: Map<String, Any> = emptyMap()) {
        Bukkit.getOnlinePlayers().forEach { player -> send(player, messageKey, args) }
    }

}