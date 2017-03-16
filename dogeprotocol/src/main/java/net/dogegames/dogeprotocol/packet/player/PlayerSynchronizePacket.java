package net.dogegames.dogeprotocol.packet.player;

import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.UUID;

public class PlayerSynchronizePacket implements DogePacket {
    private UUID uniqueId;
    private String username;
    private byte rank;
    private double dogeCoins;
    private double shiba;
    private boolean chatEnabled;
    private boolean particleEnabled;
    private boolean pmEnabled;
    private boolean partyInvitationEnabled;

    public PlayerSynchronizePacket(UUID uniqueId, String username, byte rank, double dogeCoins, double shiba,
                                   boolean chatEnabled, boolean particleEnabled, boolean pmEnabled, boolean partyInvitationEnabled) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.rank = rank;
        this.dogeCoins = dogeCoins;
        this.shiba = shiba;
        this.chatEnabled = chatEnabled;
        this.particleEnabled = particleEnabled;
        this.pmEnabled = pmEnabled;
        this.partyInvitationEnabled = partyInvitationEnabled;
    }

    public PlayerSynchronizePacket() {
    }

    @Override
    public void read(DataInput input) throws Exception {
        uniqueId = UUID.fromString(input.readUTF());
        username = input.readUTF();
        rank = input.readByte();
        dogeCoins = input.readDouble();
        shiba = input.readDouble();
        chatEnabled = input.readBoolean();
        particleEnabled = input.readBoolean();
        pmEnabled = input.readBoolean();
        partyInvitationEnabled = input.readBoolean();
    }

    @Override
    public void write(DataOutput output) throws Exception {
        output.writeUTF(uniqueId.toString());
        output.writeUTF(username);
        output.writeByte(rank);
        output.writeDouble(dogeCoins);
        output.writeDouble(shiba);
        output.writeBoolean(chatEnabled);
        output.writeBoolean(particleEnabled);
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

    public String getUsername() {
        return username;
    }

    public byte getRank() {
        return rank;
    }

    public double getDogeCoins() {
        return dogeCoins;
    }

    public double getShiba() {
        return shiba;
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public boolean isParticleEnabled() {
        return particleEnabled;
    }

    public boolean isPmEnabled() {
        return pmEnabled;
    }

    public boolean isPartyInvitationEnabled() {
        return partyInvitationEnabled;
    }
}
