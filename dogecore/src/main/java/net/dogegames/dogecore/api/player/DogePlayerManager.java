package net.dogegames.dogecore.api.player;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public interface DogePlayerManager {
    /**
     * @param player The player.
     * @return Player's data.
     */
    DogePlayer getPlayer(Player player);

    /**
     * @param uniqueId Player's unique id.
     * @return Player's data.
     */
    DogePlayer getPlayer(UUID uniqueId);

    /**
     * @return All players data.
     */
    Collection<? extends DogePlayer> getPlayers();
}
