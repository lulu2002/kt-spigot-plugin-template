package me.lulu.exampleplugin.application.util.message

interface KeyedMessage {
    fun getMessage(messageKey: String, args: Map<String, Any> = emptyMap()): String {
        return this.getListMessage(messageKey, args).joinToString("\n")
    }

    fun getListMessage(messageKey: String, args: Map<String, Any> = emptyMap()): List<String>
}