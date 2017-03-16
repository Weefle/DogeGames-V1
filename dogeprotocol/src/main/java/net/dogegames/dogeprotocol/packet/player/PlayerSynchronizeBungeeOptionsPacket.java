package net.dogegames.dogeprotocol.packet.player;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public class PlayerSynchronizeBungeeOptionsPacket implements DogePacket {
    private UUID uniqueId;
    private boolean pmEnabled;
    private boolean partyInvitationEnabled;

    public PlayerSynchronizeBungeeOptionsPacket(UUID uniqueId, boolean pmEnabled, boolean partyInvitationEnabled) {
        this.uniqueId = uniqueId;
        this.pmEnabled = pmEnabled;
        this.partyInvitationEnabled = partyInvitationEnabled;
    }

    public PlayerSynchronizeBungeeOptionsPacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        uniqueId = UUID.fromString(input.readUTF());
        pmEnabled = input.readBoolean();
        partyInvitationEnabled = input.readBoolean();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(uniqueId.toString());
        output.writeBoolean(pmEnabled);
        output.writeBoolean(partyInvitationEnabled);
    }

    @Override
    public void handle(DogePacketHandler handler) {
        handler.handle(this);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public boolean isPmEnabled() {
        return pmEnabled;
    }

    public boolean isPartyInvitationEnabled() {
        return partyInvitationEnabled;
    }
}
