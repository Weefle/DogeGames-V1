package net.dogegames.dogecore;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import net.dogegames.dogecore.api.DogeAPI;
import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.helper.ReflectionHelper;
import net.dogegames.dogecore.api.nms.ProtocolAccess;
import net.dogegames.dogecore.command.DogeCoreCommandManager;
import net.dogegames.dogecore.command.defaults.*;
import net.dogegames.dogecore.display.DogeCoreDisplayManager;
import net.dogegames.dogecore.gui.DogeCoreGuiManager;
import net.dogegames.dogecore.listener.InventoryListener;
import net.dogegames.dogecore.listener.PlayerListener;
import net.dogegames.dogecore.listener.PvPListener;
import net.dogegames.dogecore.location.DogeCoreLocationManager;
import net.dogegames.dogecore.nms.ProtocolAccess1_9;
import net.dogegames.dogecore.player.DogeCorePlayerManager;
import net.dogegames.dogecore.protocol.DogeCorePacketHandler;
import net.dogegames.dogecore.team.DogeCoreTeamManager;
import net.dogegames.dogeprotocol.pubsub.PubSubProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class DogeCore extends JavaPlugin implements DogeAPI.Impl {
    private static DogeCore instance;
    private RedisClient redisClient;
    private PubSubProtocolManager protocolManager;
    private DogeCorePacketHandler packetHandler;
    private DogeCorePlayerManager playerManager;
    private DogeCoreGuiManager guiManager;
    private ProtocolAccess protocolAccess;
    private DogeCoreCommandManager commandManager;
    private DogeCoreLocationManager locationManager;
    private DogeCoreTeamManager teamManager;
    private DogeCoreDisplayManager displayManager;
    private PvPListener pvpListener;

    public DogeCore() {
        instance = this;
    }

    /**
     * @return The {@link DogeCore} plugin.
     */
    public static DogeCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        try {
            getConfig().options().copyDefaults(true);
            getConfig().options().copyHeader(true);
            saveDefaultConfig();

            getLogger().info("Opening redis connection...");
            redisClient = RedisClient.create(RedisURI.create(getConfig().getString("environment.redis.uri")));

            getLogger().info("Loading protocol manager...");
            protocolManager = new PubSubProtocolManager(redisClient);
            protocolManager.setHandler(packetHandler = new DogeCorePacketHandler(this));

            getLogger().info("Loading player manager...");
            playerManager = new DogeCorePlayerManager(this);

            getLogger().info("Loading gui manager...");
            guiManager = new DogeCoreGuiManager();

            getLogger().info("Loading protocol access...");
            protocolAccess = new ProtocolAccess1_9();

            getLogger().info("Loading command manager...");
            commandManager = new DogeCoreCommandManager(this);

            getLogger().info("Loading location manager...");
            locationManager = new DogeCoreLocationManager(this);

            getLogger().info("Loading team manager...");
            teamManager = new DogeCoreTeamManager(this);

            getLogger().info("Loading display manager...");
            displayManager = new DogeCoreDisplayManager(this);

            getLogger().info("Loading listeners...");
            loadListeners(new PlayerListener(this), pvpListener = new PvPListener(this), new InventoryListener(this));

            getLogger().info("Loading commands...");
            unloadCommands("tps", "help", "say", "clear", "kill", "weather", "tp", "gamemode", "time");
            loadCommands(new LagCommand(), new HelpCommand(), new BroadcastCommand(), new ClearCommand(), new KillAllCommand(),
                    new KillCommand(), new WeatherCommand(), new TeleportCommand(), new GameModeCommand(), new InventoryCommand(),
                    new TimeCommand(), new WorldCommand(), new LocationCommand(), new HealCommand());

            DogeAPI.setImplementation(this);
            getLogger().info("Done!");
        } catch (Exception e) {
            getLogger().severe("Error while enabling DogeCore: " + e.getMessage());
            e.printStackTrace();
            Bukkit.shutdown();
        }
    }

    @Override
    public void onDisable() {
        try {
            ReflectionHelper.setValue(this, getClass().getSuperclass(), true, "isEnabled", true);

            pvpListener.clearShields();
            displayManager.setDisplay(null);

            getLogger().info("Saving players...");
            playerManager.saveAll();

            getLogger().info("Closing protocol manager...");
            protocolManager.close();

            getLogger().info("Closing redis connection...");
            redisClient.shutdown();

            ReflectionHelper.setValue(this, getClass().getSuperclass(), true, "isEnabled", false);
        } catch (Exception e) {
            getLogger().severe("Error while disabling DogeCore: " + e.getMessage());
            e.printStackTrace();
            Bukkit.shutdown();
        }
    }

    /**
     * @return The {@link RedisClient}.
     */
    public RedisClient getRedisClient() {
        return redisClient;
    }

    /**
     * @return The {@link DogeCorePacketHandler}.
     */
    public DogeCorePacketHandler getPacketHandler() {
        return packetHandler;
    }

    @Override
    public DogeCorePlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public DogeCoreGuiManager getGuiManager() {
        return guiManager;
    }

    @Override
    public ProtocolAccess getProtocolAccess() {
        return protocolAccess;
    }

    @Override
    public DogeCoreCommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public DogeCoreLocationManager getLocationManager() {
        return locationManager;
    }

    @Override
    public DogeCoreTeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public DogeCoreDisplayManager getDisplayManager() {
        return displayManager;
    }

    /**
     * Register listeners.
     *
     * @param listeners Listeners.
     */
    private void loadListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * Register commands.
     *
     * @param commands Commands.
     */
    private void loadCommands(DogeCommand... commands) {
        for (DogeCommand command : commands) {
            commandManager.registerCommand(command);
        }
    }

    /**
     * Unregister commands.
     *
     * @param commands Commands.
     */
    private void unloadCommands(String... commands) {
        for (String command : commands) {
            commandManager.unregisterCommand(command);
        }
    }
}
