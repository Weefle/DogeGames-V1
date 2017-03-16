package net.dogegames.dogebungee;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import net.dogegames.dogebungee.command.defaults.*;
import net.dogegames.dogebungee.listener.PlayerListener;
import net.dogegames.dogebungee.listener.ServerListener;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.DogeBungeePlayerManager;
import net.dogegames.dogebungee.protocol.DogeBungeePacketHandler;
import net.dogegames.dogeprotocol.pubsub.PubSubProtocolManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class DogeBungee extends Plugin {
    private static DogeBungee instance;
    private Configuration config;
    private MongoClient client;
    private Datastore datastore;
    private RedisClient redisClient;
    private PubSubProtocolManager protocolManager;
    private DogeBungeePlayerManager playerManager;

    public DogeBungee() {
        instance = this;
    }

    /**
     * @return The {@link DogeBungee} plugin.
     */
    public static DogeBungee getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        try {
            getLogger().info("Loading configuration...");
            config = loadConfiguration("config.yml");

            getLogger().info("Opening mongodb connection...");
            openMongoConnection();

            getLogger().info("Opening redis connection...");
            redisClient = RedisClient.create(RedisURI.create(config.getString("environment.redis.uri")));

            getLogger().info("Loading protocol manager...");
            DogeBungeePacketHandler packetHandler = new DogeBungeePacketHandler(this);
            protocolManager = new PubSubProtocolManager(redisClient);
            protocolManager.setHandler(packetHandler);

            getLogger().info("Loading player manager...");
            playerManager = new DogeBungeePlayerManager(this);

            getLogger().info("Loading listeners...");
            loadListeners(new ServerListener(this, packetHandler), new PlayerListener(this));

            getLogger().info("Loading commands...");
            loadCommands(new BroadcastCommand(), new MessageCommand(), new FriendCommand(), new KickCommand(),
                    new ServerCommand(), new SendCommand(), new BanCommand(), new PardonCommand(), new SetRankCommand(),
                    new InfoCommand(), new StaffCommand());

            getLogger().info("Done!");
        } catch (Exception e) {
            getLogger().severe("Error while enabling DogeBungee: " + e.getMessage());
            ProxyServer.getInstance().stop();
        }
    }

    @Override
    public void onDisable() {
        try {
            getLogger().info("Saving players...");
            playerManager.saveAll();

            getLogger().info("Closing protocol manager...");
            protocolManager.close();

            getLogger().info("Closing redis connection...");
            redisClient.shutdown();

            if (client != null) {
                getLogger().info("Closing mongodb connection...");
                client.close();
            }
        } catch (Exception e) {
            getLogger().severe("Error while disabling DogeBungee: " + e.getMessage());
        }
    }

    /**
     * @return The {@link Configuration}.
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * @return The mongodb {@link Datastore}.
     */
    public Datastore getDatastore() {
        return datastore;
    }

    /**
     * @return The {@link RedisClient}.
     */
    public RedisClient getRedisClient() {
        return redisClient;
    }

    /**
     * @return The {@link PubSubProtocolManager}.
     */
    public PubSubProtocolManager getProtocolManager() {
        return protocolManager;
    }

    /**
     * @return The {@link DogeBungeePlayerManager}.
     */
    public DogeBungeePlayerManager getPlayerManager() {
        return playerManager;
    }

    /**
     * Load an yaml configuration.
     *
     * @param configFile The file to load.
     * @return The {@link Configuration}.
     * @throws IOException If there is an error.
     */
    private Configuration loadConfiguration(String configFile) throws IOException {
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdirs()) getLogger().info("Creating new configuration...");
        }

        File file = new File(getDataFolder(), configFile);
        if (!file.exists()) {
            InputStream in = getResourceAsStream(configFile);
            Files.copy(in, file.toPath());
        }
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    /**
     * Open the mongodb connection.
     */
    private void openMongoConnection() {
        MongoClientURI uri = new MongoClientURI(config.getString("environment.mongo.uri"));
        client = new MongoClient(uri);
        Morphia morphia = new Morphia();
        morphia.map(DogeBungeePlayer.class);
        datastore = morphia.createDatastore(client, uri.getDatabase());
        datastore.ensureIndexes();
    }

    /**
     * Register all listeners.
     *
     * @param listeners Listeners.
     */
    private void loadListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            ProxyServer.getInstance().getPluginManager().registerListener(this, listener);
        }
    }

    /**
     * Register all commands.
     *
     * @param commands Commands.
     */
    private void loadCommands(Command... commands) {
        for (Command command : commands) {
            ProxyServer.getInstance().getPluginManager().registerCommand(this, command);
        }
    }
}
