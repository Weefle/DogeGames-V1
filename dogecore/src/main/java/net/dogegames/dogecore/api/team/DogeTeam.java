package net.dogegames.dogecore.api.team;

import net.dogegames.dogecore.api.data.DataHolder;
import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.Collection;

public interface DogeTeam extends DataHolder {
    /**
     * @return The team id.
     */
    int getId();

    /**
     * @return Name.
     */
    String getName();

    /**
     * @return Chat color.
     */
    ChatColor getChatColor();

    /**
     * @return Color.
     */
    Color getColor();

    /**
     * @return Dye color.
     */
    DyeColor getDyeColor();

    /**
     * @return If allow friendly fire.
     */
    boolean isAllowFriendlyFire();

    /**
     * Set if allow friendly fire.
     *
     * @param allowFriendlyFire If allow friendly fire.
     */
    void setAllowFriendlyFire(boolean allowFriendlyFire);

    /**
     * @return Team members.
     */
    Collection<? extends DogePlayer> getPlayers();

    /**
     * Broadcast message to the team.
     *
     * @param message Message.
     */
    void broadcast(String message);
}
