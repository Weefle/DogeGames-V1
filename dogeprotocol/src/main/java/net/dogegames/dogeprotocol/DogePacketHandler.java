package net.dogegames.dogeprotocol;

import net.dogegames.dogeprotocol.packet.player.*;
import net.dogegames.dogeprotocol.packet.proxy.ProxyBroadcastPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyPlayerJoinPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyPlayerQuitPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyStaffChatPacket;

public abstract class DogePacketHandler {
    public void handle(PlayerSynchronizePacket packet) {
    }

    public void handle(PlayerSynchronizeRequestPacket packet) {
    }

    public void handle(PlayerSynchronizeRankPacket packet) {
    }

    public void handle(PlayerSynchronizeMoneyPacket packet) {
    }

    public void handle(PlayerSynchronizeBungeeOptionsPacket packet) {
    }

    public void handle(PlayerSanctionPacket packet) {
    }

    public void handle(ProxyBroadcastPacket packet) {
    }

    public void handle(ProxyPlayerJoinPacket packet) {
    }

    public void handle(ProxyPlayerQuitPacket packet) {
    }

    public void handle(ProxyStaffChatPacket packet) {
    }
}
