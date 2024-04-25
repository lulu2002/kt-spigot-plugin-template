package me.lulu.exampleplugin

import com.google.inject.Guice
import me.lulu.exampleplugin._container.CommonModule
import me.lulu.exampleplugin._container.PluginModule
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler
import revxrsal.commands.ktx.supportSuspendFunctions


class FruitTeams : JavaPlugin() {

    private val injector = Guice.createInjector(
        PluginModule(this),
        CommonModule(),
    )

    override fun onEnable() {
        val handler = BukkitCommandHandler.create(this)
        handler.registerBrigadier()
        handler.supportSuspendFunctions()
        handler.setHelpWriter { command, actor ->
            String.format(
                "%s %s - %s",
                command.path.toRealString(),
                command.usage,
                command.description
            )
        }

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

}
