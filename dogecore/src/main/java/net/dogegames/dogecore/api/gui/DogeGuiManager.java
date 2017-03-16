package net.dogegames.dogecore.api.gui;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface DogeGuiManager {
    /**
     * Register new gui.
     *
     * @param gui The gui.
     */
    void registerGui(DogeGui gui);

    /**
     * Open the gui to the player.
     *
     * @param gui    The gui.
     * @param player The player.
     */
    void open(Class<? extends DogeGui> gui, Player player);

    /**
     * @param gui The gui's class.
     * @return The gui instance.
     */
    DogeGui getGui(Class<? extends DogeGui> gui);

    /**
     * @return Registered guis.
     */
    Collection<DogeGui> getGuis();
}
