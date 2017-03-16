package net.dogegames.dogeprotocol.packet.player;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public class PlayerSynchronizeMoneyPacket implements DogePacket {
    private UUID uniqueId;
    private double dogeCoins;
    private double shiba;

    public PlayerSynchronizeMoneyPacket(UUID uniqueId, double dogeCoins, double shiba) {
        this.uniqueId = uniqueId;
        this.dogeCoins = dogeCoins;
        this.shiba = shiba;
    }

    public PlayerSynchronizeMoneyPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        uniqueId = UUID.fromString(input.readUTF());
        dogeCoins = input.readDouble();
        shiba = input.readDouble();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(uniqueId.toString());
        output.writeDouble(dogeCoins);
        output.writeDouble(shiba);
    }

    @Override
    public void handle(DogePacketHandler handler) {
        handler.handle(this);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public double getDogeCoins() {
        return dogeCoins;
    }

    public double getShiba() {
        return shiba;
    }
}
