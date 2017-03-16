package net.dogegames.dogecore.player;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.event.player.DogePlayerJoinEvent;
import net.dogegames.dogecore.api.event.player.DogePlayerQuitEvent;
import net.dogegames.dogecore.api.permission.Rank;
import net.dogegames.dogecore.api.player.DogePlayerManager;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizePacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeRequestPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class DogeCorePlayerManager implements DogePlayerManager {
    private DogeCore plugin;
    private Collection<UUID> waitLoading;
    private Map<UUID, DogeCorePlayer> playerMap;

    public DogeCorePlayerManager(DogeCore plugin) {
        this.plugin = plugin;
        this.waitLoading = new ArrayList<>();
        this.playerMap = new HashMap<>();

        if (Bukkit.getOnlinePlayers().size() > 0) {
            plugin.getLogger().info("Synchronize online players...");
            plugin.getServer().getOnlinePlayers().forEach(player ->
                    plugin.getPacketHandler().writePacket(player, new PlayerSynchronizeRequestPacket(player.getUniqueId())));
        }
    }

    /**
     * Add player in wait loading collection.
     *
     * @param player Player's unique id.
     */
    public void waitLoading(Player player) {
        waitLoading.add(player.getUniqueId());
    }

    /**
     * Call when player join.
     *
     * @param player The player.
     * @param packet The packet.
     */
    public void playerJoin(Player player, PlayerSynchronizePacket packet) {
        DogeCorePlayer entity = new DogeCorePlayer(player.getUniqueId(), packet.getUsername(), Rank.values()[packet.getRank()],
                packet.getDogeCoins(), packet.getShiba(), new DogeCorePlayerOptions(player, packet.isChatEnabled(),
                packet.isParticleEnabled(), packet.isPmEnabled(), packet.isPartyInvitationEnabled()));
        playerMap.put(player.getUniqueId(), entity);

        if (waitLoading.contains(player.getUniqueId())) {
            DogePlayerJoinEvent event = new DogePlayerJoinEvent(entity);
            Bukkit.getPluginManager().callEvent(event);
            if (event.getJoinMessage() != null) Bukkit.broadcastMessage(event.getJoinMessage());
            waitLoading.remove(player.getUniqueId());
        }

        plugin.getLogger().info("Player " + player.getName() + " loaded!");
    }

    /**
     * Call when player quit.
     *
     * @param player The player.
     */
    public void playerQuit(Player player) {
        if (!playerMap.containsKey(player.getUniqueId())) {
            plugin.getLogger().info("Unable to save " + player.getName() + " because he is not loaded.");
            return;
        }
        DogeCorePlayer entity = playerMap.get(player.getUniqueId());
        DogePlayerQuitEvent event = new DogePlayerQuitEvent(entity);
        Bukkit.getPluginManager().callEvent(event);
        if (event.getQuitMessage() != null) Bukkit.broadcastMessage(event.getQuitMessage());
        if (entity.getTeam() != null) entity.getTeam().removePlayer(entity);
        save(entity);
        playerMap.remove(player.getUniqueId());
    }

    /**
     * Save player's data.
     *
     * @param entity The {@link DogeCorePlayer}.
     */
    public void save(DogeCorePlayer entity) {
        plugin.getPacketHandler().writePacket(Bukkit.getPlayer(entity.getUniqueId()), new PlayerSynchronizePacket(
                entity.getUniqueId(), entity.getUsername(), (byte) entity.getRank().ordinal(), entity.getDogeCoins(),
                entity.getShiba(), entity.getOptions().isChatEnabled(), entity.getOptions().isParticleEnabled(),
                entity.getOptions().isPmEnabled(), entity.getOptions().isPartyInvitationEnabled()));
    }

    /**
     * Save all players.
     */
    public void saveAll() {
        playerMap.values().forEach(this::save);
    }

    @Override
    public DogeCorePlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    @Override
    public DogeCorePlayer getPlayer(UUID uniqueId) {
        return playerMap.get(uniqueId);
    }

    @Override
    public Collection<? extends DogeCorePlayer> getPlayers() {
        return Collections.unmodifiableCollection(playerMap.values());
    }
}
