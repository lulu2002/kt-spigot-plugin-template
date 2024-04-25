import me.lulu.exampleplugin.application.util.message.MessengerSpy
import org.bukkit.command.CommandSender

fun MessengerSpy.assertSentTeamNotExist(sender: CommandSender, teamId: String) {
    assertSent(sender, "team.not-exists", mapOf("team" to teamId))
}
