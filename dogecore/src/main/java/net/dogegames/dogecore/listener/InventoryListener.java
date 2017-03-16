package net.dogegames.dogecore.listener;

import net.dogegames.dogecore.DogeCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {
    private DogeCore plugin;

    public InventoryListener(DogeCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();

        plugin.getGuiManager().getGuis().stream()
                .filter(gui -> event.getInventory().getName().equals(gui.titleFor(player)))
                .forEach(gui -> gui.onOpen(player, event.getInventory()));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        plugin.getGuiManager().getGuis().stream()
                .filter(gui -> event.getInventory().getName().equals(gui.titleFor(player)))
                .forEach(gui -> gui.onClose(player, event.getInventory()));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
            plugin.getGuiManager().getGuis().stream()
                    .filter(gui -> event.getInventory().getName().equals(gui.titleFor(player)))
                    .forEach(gui -> {
                        gui.onClick(player, event.getInventory(), event.getCurrentItem(), event.getSlot(), event.getClick());
                        event.setCancelled(!gui.canTake(player, event.getCurrentItem()));
                    });
        }
    }
}
