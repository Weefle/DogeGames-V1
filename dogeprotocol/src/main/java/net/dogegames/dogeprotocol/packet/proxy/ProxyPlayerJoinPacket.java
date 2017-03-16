package net.dogegames.dogeprotocol.packet.proxy;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public class ProxyPlayerJoinPacket implements DogePacket {
    private UUID uniqueId;
    private String username;

    public ProxyPlayerJoinPacket(UUID uniqueId, String username) {
        this.uniqueId = uniqueId;
        this.username = username;
    }

    public ProxyPlayerJoinPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        uniqueId = UUID.fromString(input.readUTF());
        username = input.readUTF();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(uniqueId.toString());
        output.writeUTF(username);
    }

    @Override
    public void handle(DogePacketHandler handler) {
        handler.handle(this);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getUsername() {
        return username;
    }
}
