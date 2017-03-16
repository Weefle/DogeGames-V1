package net.dogegames.dogebungee.listener;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.defaults.FriendCommand;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogeprotocol.packet.proxy.ProxyPlayerJoinPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyStaffChatPacket;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {
    private DogeBungee plugin;
    private Title title;

    public PlayerListener(DogeBungee plugin) {
        this.plugin = plugin;
        this.title = ProxyServer.getInstance().createTitle().title(TextComponent.fromLegacyText("§6DogeGames"))
                .subTitle(TextComponent.fromLegacyText("§aw§eo§bw")).fadeIn(20).stay(40).fadeOut(10);
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        DogeBungeePlayer entity = plugin.getPlayerManager().playerJoin(event.getConnection());

        if (entity.getBan() != null) {
            event.setCancelReason(entity.getBan().getMessage());
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(player);
        plugin.getProtocolManager().writePacket(new ProxyPlayerJoinPacket(entity.getUniqueId(), entity.getUsername()));

        title.send(player);
        player.sendMessage(TextComponent.fromLegacyText("§6Bienvenue §b" + player.getName() + " §6sur §bDogeGames §e!"));
        FriendCommand.sendFriendList(player, false);

        if (entity.getMute() != null) {
            player.sendMessage(TextComponent.fromLegacyText("§cRappel : " + entity.getMute().getMessage()));
        }
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(player);

        plugin.getProtocolManager().writePacket(new ProxyPlayerJoinPacket(entity.getUniqueId(), entity.getUsername()));
        plugin.getPlayerManager().playerQuit(player);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (event.isCommand()) return;
        DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer((ProxiedPlayer) event.getSender());
        if (entity.isUsingStaffChat()) {
            DogeBungee.getInstance().getProtocolManager().writePacket(new ProxyStaffChatPacket(
                    entity.getUsername(),
                    (byte) entity.getRank().ordinal(),
                    event.getMessage()));
            event.setCancelled(true);
        }
    }
}
