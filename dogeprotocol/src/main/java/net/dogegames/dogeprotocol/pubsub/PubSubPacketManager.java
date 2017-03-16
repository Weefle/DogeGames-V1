package net.dogegames.dogeprotocol.pubsub;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.dogegames.dogeprotocol.packet.DogePacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSanctionPacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeMoneyPacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeRankPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyBroadcastPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyPlayerJoinPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyPlayerQuitPacket;
import net.dogegames.dogeprotocol.packet.proxy.ProxyStaffChatPacket;

import java.io.DataInput;
import java.io.DataOutput;

public final class PubSubPacketManager {
    private static final BiMap<Byte, Class<? extends DogePacket>> packets = HashBiMap.create();

    static {
        packets.put((byte) 0x01, PlayerSynchronizeRankPacket.class);
        packets.put((byte) 0x02, PlayerSynchronizeMoneyPacket.class);
        packets.put((byte) 0x03, ProxyBroadcastPacket.class);
        packets.put((byte) 0x04, ProxyPlayerJoinPacket.class);
        packets.put((byte) 0x05, ProxyPlayerQuitPacket.class);
        packets.put((byte) 0x06, ProxyStaffChatPacket.class);
    }

    /**
     * Read a {@link DogePacket} from a {@link DataInput}.
     *
     * @param input The input.
     * @return The {@link DogePacket}.
     * @throws Exception If there is an exception.
     */
    public static DogePacket readPacket(DataInput input) throws Exception {
        byte packetId = input.readByte();
        if (!packets.containsKey(packetId)) {
            throw new RuntimeException("This packet isn't registered!");
        }
        DogePacket packet = packets.get(packetId).newInstance();
        packet.read(input);
        return packet;
    }

    /**
     * Write a {@link DogePacket} on {@link DataOutput}.
     *
     * @param output The output.
     * @param packet The packet.
     * @throws Exception If there is an exception.
     */
    public static void writePacket(DataOutput output, DogePacket packet) throws Exception {
        if (!packets.inverse().containsKey(packet.getClass())) {
            throw new RuntimeException("This packet isn't registered!");
        }
        output.writeByte(packets.inverse().get(packet.getClass()));
        packet.write(output);
    }
}
