package net.dogegames.dogeprotocol.packet.player;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public class PlayerSanctionPacket implements DogePacket {
    private byte type;
    private UUID target;
    private String reason;
    private long finish;
    private UUID moderator;

    public PlayerSanctionPacket(byte type, UUID target, String reason, long finish, UUID moderator) {
        this.type = type;
        this.target = target;
        this.reason = reason;
        this.finish = finish;
        this.moderator = moderator;
    }

    public PlayerSanctionPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        type = input.readByte();
        target = UUID.fromString(input.readUTF());
        reason = input.readUTF();
        finish = input.readLong();
        moderator = UUID.fromString(input.readUTF());
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeByte(type);
        output.writeUTF(target.toString());
        output.writeUTF(reason);
        output.writeLong(finish);
        output.writeUTF(moderator.toString());
    }

    @Override
    public void handle(DogePacketHandler handler) {
        handler.handle(this);
    }

    public byte getType() {
        return type;
    }

    public UUID getTarget() {
        return target;
    }

    public String getReason() {
        return reason;
    }

    public long getFinish() {
        return finish;
    }

    public UUID getModerator() {
        return moderator;
    }
}
