package net.dogegames.dogecore.display;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.display.DogeDisplay;
import net.dogegames.dogecore.api.display.DogeDisplayManager;
import org.bukkit.Bukkit;

public class DogeCoreDisplayManager implements DogeDisplayManager {
    private DogeCore plugin;
    private DogeDisplay display;

    public DogeCoreDisplayManager(DogeCore plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (getDisplay() != null) {
                plugin.getPlayerManager().getPlayers().forEach(player ->
                        getDisplay().display(player, player.getBar(), player.getScoreboard()));
            }
        }, 0L, 20L);
    }

    @Override
    public DogeDisplay getDisplay() {
        return display;
    }

    @Override
    public void setDisplay(DogeDisplay display) {
        boolean update = this.display != null && display == null;
        this.display = display;
        if (update) {
            plugin.getPlayerManager().getPlayers().forEach(player -> {
                player.getBar().setEnabled(false);
                player.getScoreboard().setEnabled(false);
            });
        }
    }
}
