package net.dogegames.dogebungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.helper.TextHelper;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.protocol.DogeBungeePacketHandler;
import net.dogegames.dogeprotocol.bungee.BungeePacketManager;
import net.dogegames.dogeprotocol.packet.DogePacket;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;

public class ServerListener implements Listener {
    private DogeBungee plugin;
    private DogeBungeePacketHandler packetHandler;
    private Favicon favicon;

    public ServerListener(DogeBungee plugin, DogeBungeePacketHandler packetHandler) {
        this.plugin = plugin;
        this.packetHandler = packetHandler;
        try {
            this.favicon = Favicon.create(ImageIO.read(plugin.getResourceAsStream("favicon.png")));
        } catch (Exception e) {
            plugin.getLogger().severe("Error while reading favicon: " + e.getMessage());
        }
    }

    @EventHandler
    public void onMessage(PluginMessageEvent event) {
        if (event.getTag().equals("DogeProtocol")) {
            try {
                ByteArrayDataInput input = ByteStreams.newDataInput(event.getData());
                DogePacket packet = BungeePacketManager.readPacket(input);
                packet.handle(packetHandler);
            } catch (Exception e) {
                plugin.getLogger().severe("Error while reading packet on plugin messaging: " + e.getMessage());
            }
        }
    }

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        String header = "§aw§eo§bw";
        if (ProxyServer.getInstance().getOnlineCount() > 0) {
            header += " §7" + ProxyServer.getInstance().getOnlineCount() + " " + TextHelper.pluralize("doge", ProxyServer.getInstance().getOnlineCount()) + " !";
        }
        event.getResponse().setVersion(new ServerPing.Protocol(header, 9000));
        event.getResponse().setDescriptionComponent(new TextComponent("§f➜ §6DogeGames §f| §b1.9.x §f- §b1.10 §f| §9www.dogegames.net\n§f➜ " + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd"))));
        if (favicon != null) event.getResponse().setFavicon(favicon);
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(player);
        entity.writeMessage(entity.getSynchronizePacket());
    }
}
