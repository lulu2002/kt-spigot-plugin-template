package me.lulu.exampleplugin.application.util.message

import org.bukkit.command.CommandSender

class MessengerSpy : Messenger {

    private val sentMessages = mutableListOf<SentMessage>()

    override fun send(player: CommandSender, messageKey: String, args: Map<String, Any>) {
        sentMessages.add(SentMessage(player, messageKey, args))
    }

    override fun send(players: Iterable<CommandSender>, messageKey: String, args: Map<String, Any>) {
        players.forEach { send(it, messageKey, args) }
    }

    fun assertSent(player: CommandSender, messageKey: String, args: Map<String, Any> = emptyMap()) {
        val sentMessage = sentMessages.find { it.player == player && it.messageKey == messageKey && it.args == args }
        if (sentMessage == null) {
            throw AssertionError("Expected message not sent")
        }
    }

    fun assertSentNothing(player: CommandSender) {
        val sentMessage = sentMessages.find { it.player == player }
        if (sentMessage != null) {
            throw AssertionError("Expected no message sent")
        }
    }

    fun assertSent(players: Iterable<CommandSender>, messageKey: String, args: Map<String, Any> = emptyMap()) {
        players.forEach { assertSent(it, messageKey, args) }
    }

    private data class SentMessage(
        val player: CommandSender,
        val messageKey: String,
        val args: Map<String, Any>
    )
}