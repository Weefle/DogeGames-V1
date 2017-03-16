package net.dogegames.dogeprotocol.packet.player;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public class PlayerSynchronizeRankPacket implements DogePacket {
    private UUID uniqueId;
    private byte rank;

    public PlayerSynchronizeRankPacket(UUID uniqueId, byte rank) {
        this.uniqueId = uniqueId;
        this.rank = rank;
    }

    public PlayerSynchronizeRankPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        uniqueId = UUID.fromString(input.readUTF());
        rank = input.readByte();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(uniqueId.toString());
        output.writeByte(rank);
    }

    @Override
    public void handle(DogePacketHandler handler) {
        handler.handle(this);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public byte getRank() {
        return rank;
    }
}

