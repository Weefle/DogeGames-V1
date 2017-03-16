package net.dogegames.dogebungee.protocol;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.DogeBungeePlayerSanction;
import net.dogegames.dogebungee.player.permission.Rank;
import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.player.*;
import net.dogegames.dogeprotocol.packet.proxy.ProxyBroadcastPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyPlayerJoinPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyPlayerQuitPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyStaffChatPacket;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Date;

public class DogeBungeePacketHandler extends DogePacketHandler {
    private DogeBungee plugin;

    public DogeBungeePacketHandler(DogeBungee plugin) {
        this.plugin = plugin;

        ProxyServer.getInstance().registerChannel("DogeProtocol");
    }

    @Override
    public void handle(PlayerSynchronizePacket packet) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(packet.getUniqueId());
        if (player != null) {
            DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(packet.getUniqueId());
            entity.setDogeCoins(packet.getDogeCoins());
            entity.setShiba(packet.getShiba());
            entity.getOptions().setChatEnabled(packet.isChatEnabled());
            entity.getOptions().setParticleEnabled(packet.isParticleEnabled());
            entity.getOptions().setPmEnabled(packet.isPmEnabled());
            entity.getOptions().setPartyInvitationEnabled(packet.isPartyInvitationEnabled());
        }
    }

    @Override
    public void handle(PlayerSynchronizeRequestPacket packet) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(packet.getUniqueId());
        if (player != null) {
            DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(packet.getUniqueId());
            entity.writeMessage(entity.getSynchronizePacket());
        }
    }

    @Override
    public void handle(PlayerSynchronizeRankPacket packet) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(packet.getUniqueId());
        if (player != null) {
            DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(player);
            entity.setRank(Rank.values()[packet.getRank()]);
        }
    }

    @Override
    public void handle(PlayerSynchronizeMoneyPacket packet) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(packet.getUniqueId());
        if (player != null) {
            DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(player);
            entity.setDogeCoins(packet.getDogeCoins());
            entity.setShiba(packet.getShiba());
        }
    }

    @Override
    public void handle(PlayerSynchronizeBungeeOptionsPacket packet) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(packet.getUniqueId());
        if (player != null) {
            DogeBungeePlayer entity = plugin.getPlayerManager().getPlayer(player);
            entity.getOptions().setPmEnabled(packet.isPmEnabled());
            entity.getOptions().setPartyInvitationEnabled(packet.isPartyInvitationEnabled());
        }
    }

    @Override
    public void handle(PlayerSanctionPacket packet) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(packet.getTarget());
        if (player != null) {
            DogeBungeePlayer target = plugin.getPlayerManager().getPlayer(packet.getTarget());
            DogeBungeePlayer moderator = plugin.getPlayerManager().getPlayer(packet.getModerator());
            if (moderator == null) {
                moderator = DogeBungee.getInstance().getDatastore().find(DogeBungeePlayer.class).field("uniqueId").equalIgnoreCase(packet.getModerator()).get();
            }
            DogeBungeePlayerSanction sanction = new DogeBungeePlayerSanction(DogeBungeePlayerSanction.SanctionType.values()[packet.getType()],
                    moderator, packet.getReason(), new Date(packet.getFinish()));

            target.getSanctions().add(sanction);

            if (sanction.getType() == DogeBungeePlayerSanction.SanctionType.BAN) {
                player.disconnect(TextComponent.fromLegacyText(target.getBan().getMessage()));
            } else if (sanction.getType() == DogeBungeePlayerSanction.SanctionType.MUTE) {
                player.sendMessage(TextComponent.fromLegacyText(target.getMute().getMessage()));
            }
        }
    }

    @Override
    public void handle(ProxyBroadcastPacket packet) {
        ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText("§f[§bMessage§f] §a" + packet.getMessage()));
    }

    @Override
    public void handle(ProxyPlayerJoinPacket packet) {
        BaseComponent[] notification = TextComponent.fromLegacyText("§f[§bAmis§f] §a" + packet.getUsername() + " vient de se connecter.");
        plugin.getPlayerManager().getPlayers().forEach(target -> target.getFriends().forEach(targetFriend -> {
            if (targetFriend.getUniqueId().equals(packet.getUniqueId())) {
                ProxyServer.getInstance().getPlayer(target.getUniqueId()).sendMessage(notification);
            }
        }));
    }

    @Override
    public void handle(ProxyPlayerQuitPacket packet) {
        BaseComponent[] notification = TextComponent.fromLegacyText("§f[§bAmis§f] §c" + packet.getUsername() + " vient de se déconnecter.");
        plugin.getPlayerManager().getPlayers().forEach(target -> target.getFriends().forEach(targetFriend -> {
            if (targetFriend.getUniqueId().equals(packet.getUniqueId())) {
                ProxyServer.getInstance().getPlayer(target.getUniqueId()).sendMessage(notification);
            }
        }));
    }

    @Override
    public void handle(ProxyStaffChatPacket packet) {
        BaseComponent[] component = TextComponent.fromLegacyText("§f[§bStaff§f] §f[" + Rank.values()[packet.getRank()].getColor() + packet.getUsername() + "§f] §a" + packet.getMessage());
        DogeBungee.getInstance().getPlayerManager().getPlayers().stream()
                .filter(entity -> entity.getRank().getPower() >= Rank.HELPER.getPower())
                .forEach(entity -> ProxyServer.getInstance().getPlayer(entity.getUniqueId()).sendMessage(component));
    }
}
