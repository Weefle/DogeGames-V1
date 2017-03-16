package net.dogegames.dogeprotocol.packet.proxy;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;

public class ProxyBroadcastPacket implements DogePacket {
    private String message;

    public ProxyBroadcastPacket(String message) {
        this.message = message;
    }

    public ProxyBroadcastPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        message = input.readUTF();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(message);
    }

    @Override
    public void handle(DogePacketHandler handler) {
        handler.handle(this);
    }

    public String getMessage() {
        return message;
    }
}
