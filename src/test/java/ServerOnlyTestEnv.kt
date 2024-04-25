import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.WorldMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import be.seeseemelk.mockbukkit.entity.PlayerMockFactory
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class ServerOnlyTestEnv {

    protected lateinit var server: ServerMock
        private set
    private lateinit var playerMockFactory: PlayerMockFactory

    val pluginManager: PluginManagerMock
        get() = server.pluginManager

    @BeforeEach
    fun setupServer() {
        unloadIfAlreadyMocking()
        server = MockBukkit.mock(ServerMock())
        playerMockFactory = PlayerMockFactory(server)
    }

    private fun unloadIfAlreadyMocking() {
        if (MockBukkit.isMocked()) {
            MockBukkit.unmock()
        }
    }


    fun createRandomPlayer(): PlayerMock {
        return playerMockFactory.createRandomPlayer()
    }

    fun createSimpleWorld(name: String): WorldMock {
        return server.addSimpleWorld(name)
    }

    fun createRandomOnlinePlayer(): PlayerMock {
        return createRandomPlayer().also { server.addPlayer(it) }
    }

    @AfterEach
    fun tearDownServer() {
        MockBukkit.unmock()
    }

}