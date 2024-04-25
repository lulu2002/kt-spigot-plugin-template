package me.lulu.exampleplugin.application.util

import net.md_5.bungee.api.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern


private val pattern = Pattern.compile("#[a-fA-F0-9]{6}")

fun String.colored(): String {
    return this.processHexColor().let { ChatColor.translateAlternateColorCodes('&', it) }
}

fun List<String>.colored(): List<String> {
    return this.map { it.colored() }
}

private fun String.processHexColor(): String {
    var result = this
    var matcher: Matcher = pattern.matcher(result)

    while (matcher.find()) {
        val color = result.substring(matcher.start(), matcher.end())
        result = result.replace(color, ChatColor.of(color).toString() + "")
        matcher = pattern.matcher(result)
    }

    return result
}
