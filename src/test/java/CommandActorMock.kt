import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.ComponentLike
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.BukkitCommandHandler
import revxrsal.commands.bukkit.exception.SenderNotPlayerException
import java.util.*

class CommandActorMock(private val sender: CommandSender) : BukkitCommandActor {

    override fun getName(): String {
        return sender.name
    }

    override fun getUniqueId(): UUID {
        if (sender is Player) {
            return sender.uniqueId
        }

        throw UnsupportedOperationException("Cannot get UUID of non-player sender")
    }

    override fun reply(component: ComponentLike) {
        sender.sendMessage(component)
    }

    override fun reply(p0: String) {
        sender.sendMessage(p0)
    }

    override fun error(p0: String) {
        sender.sendMessage(p0)
    }

    override fun getCommandHandler(): BukkitCommandHandler? {
        return null
    }

    override fun getSender(): CommandSender {
        return sender
    }

    override fun isPlayer(): Boolean {
        return sender is Player
    }

    override fun isConsole(): Boolean {
        return sender is ConsoleCommandSender
    }

    override fun getAsPlayer(): Player? {
        return if (isPlayer()) sender as Player else null
    }

    override fun requirePlayer(): Player {
        if (!this.isPlayer()) {
            throw SenderNotPlayerException();
        } else {
            return this.sender as Player;
        }
    }

    override fun requireConsole(): ConsoleCommandSender {
        if (!this.isConsole()) {
            throw SenderNotPlayerException();
        } else {
            return this.sender as ConsoleCommandSender;
        }
    }

    override fun audience(): Audience {
        TODO()
    }
}


fun CommandSender.asMockActor(): CommandActorMock {
    return CommandActorMock(this)
}