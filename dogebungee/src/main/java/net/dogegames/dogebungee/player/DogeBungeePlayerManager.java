package net.dogegames.dogebungee.player;

import net.dogegames.dogebungee.DogeBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class DogeBungeePlayerManager {
    private DogeBungee plugin;
    private Map<UUID, DogeBungeePlayer> playerMap;

    public DogeBungeePlayerManager(DogeBungee plugin) {
        this.plugin = plugin;
        this.playerMap = new HashMap<>();
    }

    /**
     * Call when player join.
     *
     * @param pendingConnection The pending connection.
     * @return The {@link DogeBungeePlayer}.
     */
    public DogeBungeePlayer playerJoin(PendingConnection pendingConnection) {
        long started = System.currentTimeMillis();
        boolean exists = true;
        try {
            DogeBungeePlayer entity = plugin.getDatastore().find(DogeBungeePlayer.class, "uniqueId", pendingConnection.getUniqueId()).get();
            if (entity == null) {
                exists = false;
                entity = new DogeBungeePlayer(pendingConnection.getUniqueId(), pendingConnection.getName());
            }
            entity.setUsername(pendingConnection.getName());
            entity.setLastConnection(new Date());
            playerMap.put(pendingConnection.getUniqueId(), entity);

            if (!exists) save(entity);

            plugin.getLogger().info("Player " + pendingConnection.getName() + " loaded in " + (System.currentTimeMillis() - started) + "ms");
            return entity;
        } catch (Exception e) {
            plugin.getLogger().severe("Error while loading player " + pendingConnection.getName() + ": " + e.getMessage());
            pendingConnection.disconnect(TextComponent.fromLegacyText("§cImpossible de récupérer vos données.\n§cMerci de contacter un administrateur."));
        }
        return null;
    }

    /**
     * Call when player quit.
     *
     * @param player The player.
     */
    public void playerQuit(ProxiedPlayer player) {
        if (playerMap.containsKey(player.getUniqueId())) {
            DogeBungeePlayer entity = playerMap.get(player.getUniqueId());
            entity.setLastConnection(new Date());
            save(entity);
            playerMap.remove(player.getUniqueId());
        }
    }

    /**
     * Save player's data.
     *
     * @param entity The {@link DogeBungeePlayer}.
     */
    public void save(DogeBungeePlayer entity) {
        long started = System.currentTimeMillis();
        plugin.getDatastore().save(entity);
        plugin.getLogger().info("Player " + entity.getUsername() + " saved in " + (System.currentTimeMillis() - started) + "ms");
    }

    /**
     * Save all players.
     */
    public void saveAll() {
        playerMap.values().forEach(this::save);
    }

    /**
     * @param player The player.
     * @return Player's data.
     */
    public DogeBungeePlayer getPlayer(ProxiedPlayer player) {
        return getPlayer(player.getUniqueId());
    }

    /**
     * @param uniqueId Player's unique id.
     * @return Player's data.
     */
    public DogeBungeePlayer getPlayer(UUID uniqueId) {
        return playerMap.get(uniqueId);
    }

    /**
     * @return All players data.
     */
    public Collection<DogeBungeePlayer> getPlayers() {
        return Collections.unmodifiableCollection(playerMap.values());
    }
}
