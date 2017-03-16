package net.dogegames.dogecore.listener;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.event.player.DogePlayerDamageEvent;
import net.dogegames.dogecore.api.event.player.DogePlayerTalkEvent;
import net.dogegames.dogecore.player.DogeCorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PlayerListener implements Listener {
    private DogeCore plugin;

    public PlayerListener(DogeCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        event.allow();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);
        plugin.getPlayerManager().waitLoading(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);
        plugin.getPlayerManager().playerQuit(player);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        DogeCorePlayer entity = plugin.getPlayerManager().getPlayer(player);

        DogePlayerTalkEvent playerTalkEvent = new DogePlayerTalkEvent(entity, event.getFormat(), event.getMessage());
        Bukkit.getPluginManager().callEvent(playerTalkEvent);

        event.setFormat(playerTalkEvent.getFormat());
        event.setMessage(playerTalkEvent.getMessage());
        event.setCancelled(playerTalkEvent.isCancelled());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player || event.getDamager() instanceof Arrow) {
            Player victim = (Player) event.getEntity();
            Player damager;
            if (event.getDamager() instanceof Arrow) {
                ProjectileSource source = ((Arrow) event.getDamager()).getShooter();
                if (source instanceof Player) damager = (Player) source;
                else return;
            } else {
                damager = (Player) event.getDamager();
            }

            DogePlayerDamageEvent damageEvent = new DogePlayerDamageEvent(plugin.getPlayerManager().getPlayer(victim),
                    plugin.getPlayerManager().getPlayer(victim));
            Bukkit.getPluginManager().callEvent(damageEvent);
            event.setCancelled(damageEvent.isCancelled());
        }
    }
}
