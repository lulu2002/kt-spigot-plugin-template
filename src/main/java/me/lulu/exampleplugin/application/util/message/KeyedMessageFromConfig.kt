package me.lulu.exampleplugin.application.util.message

import me.lulu.exampleplugin.application.util.colored
import me.lulu.exampleplugin.application.util.config.MessageConfig


class KeyedMessageFromConfig(private val messageConfig: MessageConfig) : KeyedMessage {

    override fun getListMessage(messageKey: String, args: Map<String, Any>): List<String> {
        val unparsed = this.messageConfig.stringList(messageKey).colored()
        return unparsed.map { this.parse(it, args) }
    }

    private fun parse(unparsed: String, args: Map<String, Any>): String {
        return args.entries.fold(unparsed) { acc, entry ->
            acc.replace("<${entry.key}>", entry.value.toString().colored())
        }
    }

}