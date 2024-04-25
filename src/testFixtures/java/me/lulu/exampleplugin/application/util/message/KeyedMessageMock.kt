package me.lulu.exampleplugin.application.util.message

open class KeyedMessageMock : KeyedMessage {
    private val listMessages = mutableMapOf<String, List<String>>()

    override fun getListMessage(messageKey: String, args: Map<String, Any>): List<String> {
        val listMsg = listMessages[messageKey] ?: listOf("No message found for key $messageKey")
        return listMsg.map { message -> replaceArgsInMessage(message, args) }
    }

    private fun replaceArgsInMessage(message: String, args: Map<String, Any>): String {
        return args.entries.fold(message) { acc, (key, value) ->
            acc.replace("<$key>", value.toString())
        }
    }

    fun addMockedMessage(messageKey: String, message: String) {
        listMessages[messageKey] = listOf(message)
    }

    fun addMockedListMessage(messageKey: String, message: List<String>) {
        listMessages[messageKey] = message
    }
}