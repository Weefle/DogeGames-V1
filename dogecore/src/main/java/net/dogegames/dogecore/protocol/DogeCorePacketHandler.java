package net.dogegames.dogecore.protocol;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.permission.Rank;
import net.dogegames.dogecore.player.DogeCorePlayer;
import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.bungee.BungeePacketManager;
import net.dogegames.dogeprotocol.packet.DogePacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeBungeeOptionsPacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeMoneyPacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizePacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeRankPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class DogeCorePacketHandler extends DogePacketHandler implements PluginMessageListener {
    private DogeCore plugin;

    public DogeCorePacketHandler(DogeCore plugin) {
        this.plugin = plugin;

        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "DogeProtocol");
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "DogeProtocol", this);
    }

    /**
     * Send a packet on the plugin messaging.
     *
     * @param player The player.
     * @param packet The packet.
     */
    public void writePacket(Player player, DogePacket packet) {
        try {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            BungeePacketManager.writePacket(output, packet);
            player.sendPluginMessage(plugin, "DogeProtocol", output.toByteArray());
        } catch (Exception e) {
            plugin.getLogger().severe("Error while writing packet on plugin messaging: " + e.getMessage());
        }
    }

    @Override
    public void onPluginMessageReceived(String tag, Player player, byte[] data) {
        if (tag.equals("DogeProtocol")) {
            try {
                ByteArrayDataInput input = ByteStreams.newDataInput(data);
                DogePacket packet = BungeePacketManager.readPacket(input);
                packet.handle(this);
            } catch (Exception e) {
                plugin.getLogger().severe("Error while reading packet on plugin messaging: " + e.getMessage());
            }
        }
    }

    @Override
    public void handle(PlayerSynchronizePacket packet) {
        plugin.getPlayerManager().playerJoin(Bukkit.getPlayer(packet.getUniqueId()), packet);
    }

    @Override
    public void handle(PlayerSynchronizeRankPacket packet) {
        DogeCorePlayer entity = plugin.getPlayerManager().getPlayer(packet.getUniqueId());
        entity.setRank(Rank.values()[packet.getRank()]);
    }

    @Override
    public void handle(PlayerSynchronizeMoneyPacket packet) {
        DogeCorePlayer entity = plugin.getPlayerManager().getPlayer(packet.getUniqueId());
        entity.setDogeCoins(packet.getDogeCoins());
        entity.setShiba(packet.getShiba());
    }

    @Override
    public void handle(PlayerSynchronizeBungeeOptionsPacket packet) {
        DogeCorePlayer entity = plugin.getPlayerManager().getPlayer(packet.getUniqueId());
        entity.getOptions().update(packet.isPmEnabled(), packet.isPartyInvitationEnabled());
    }
}
