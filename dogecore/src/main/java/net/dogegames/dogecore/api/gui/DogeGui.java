package net.dogegames.dogecore.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class DogeGui {
    /**
     * @param player The player.
     * @return The gui title.
     */
    public abstract String titleFor(Player player);

    /**
     * Define the gui content.
     *
     * @param player    The player.
     * @param inventory The inventory.
     */
    public abstract void contentFor(Player player, Inventory inventory);

    /**
     * @return The gui size (min: 9, max: 54).
     */
    public int getSize() {
        return 9;
    }

    /**
     * @param player The player.
     * @param item   The item.
     * @return If the player can take the item.
     */
    public boolean canTake(Player player, ItemStack item) {
        return false;
    }

    /**
     * Call when player click on the gui.
     *
     * @param player    The player.
     * @param inventory The inventory.
     * @param item      The item.
     * @param position  The item position.
     * @param type      The click type.
     */
    public void onClick(Player player, Inventory inventory, ItemStack item, int position, ClickType type) {
    }

    /**
     * Call when player open the gui.
     *
     * @param player    The player.
     * @param inventory The inventory.
     */
    public void onOpen(Player player, Inventory inventory) {
    }

    /**
     * Call when player close the gui.
     *
     * @param player    The player.
     * @param inventory The inventory.
     */
    public void onClose(Player player, Inventory inventory) {
    }
}
