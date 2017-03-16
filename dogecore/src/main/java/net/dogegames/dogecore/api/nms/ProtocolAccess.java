package net.dogegames.dogecore.api.nms;

import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

public interface ProtocolAccess {
    /**
     * Send tablist packet.
     *
     * @param player The player.
     * @param header The header.
     * @param footer The footer.
     */
    void sendTablist(Player player, String header, String footer);

    /**
     * Send an actionbar message.
     *
     * @param player  The player.
     * @param message The message.
     */
    void sendActionbarMessage(Player player, String message);

    /**
     * Send title.
     *
     * @param player   The player.
     * @param fadeIn   Fade in time in tick.
     * @param stay     Stay time in tick.
     * @param fadeOut  Fade out time in tick.
     * @param title    The title.
     * @param subtitle The sub title.
     */
    void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle);

    /**
     * Register a command.
     *
     * @param command The command.
     */
    void registerCommand(Command command);

    /**
     * Unregister a command.
     *
     * @param name The command name.
     */
    void unregisterCommand(String name);

    /**
     * Set the command map.
     *
     * @param map The new command map.
     */
    void setCommandMap(SimpleCommandMap map);

    /**
     * @return Server's TPS.
     */
    double[] getTps();

    /**
     * @param player The player.
     * @return Player's ping.
     */
    int getPing(Player player);
}
