package me.lulu.exampleplugin.application.util.message

import org.bukkit.command.CommandSender

class MessengerImpl(private val keyedMessage: KeyedMessage) : Messenger {

    override fun send(player: CommandSender, messageKey: String, args: Map<String, Any>) {
        keyedMessage.getListMessage(messageKey, args).forEach { player.sendMessage(it) }
    }

    override fun send(players: Iterable<CommandSender>, messageKey: String, args: Map<String, Any>) {
        players.forEach { this.send(it, messageKey, args) }
    }

}

