package net.dogegames.dogeprotocol.packet.proxy;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;

public class ProxyStaffChatPacket implements DogePacket {
    private String username;
    private byte rank;
    private String message;

    public ProxyStaffChatPacket(String username, byte rank, String message) {
        this.username = username;
        this.rank = rank;
        this.message = message;
    }

    public ProxyStaffChatPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        username = input.readUTF();
        rank = input.readByte();
        message = input.readUTF();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(username);
        output.writeByte(rank);
        output.writeUTF(message);
    }

    @Override
    public void handle(DogePacketHandler handler) {
        handler.handle(this);
    }

    public String getUsername() {
        return username;
    }

    public byte getRank() {
        return rank;
    }

    public String getMessage() {
        return message;
    }
}
