package net.dogegames.dogecore.player;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.player.DogePlayerOptions;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeBungeeOptionsPacket;
import org.bukkit.entity.Player;

public class DogeCorePlayerOptions implements DogePlayerOptions {
    private Player player;
    private boolean chatEnabled;
    private boolean particleEnabled;
    private boolean pmEnabled;
    private boolean partyInvitationEnabled;

    DogeCorePlayerOptions(Player player, boolean chatEnabled, boolean particleEnabled, boolean pmEnabled, boolean partyInvitationEnabled) {
        this.chatEnabled = chatEnabled;
        this.particleEnabled = particleEnabled;
        this.pmEnabled = pmEnabled;
        this.partyInvitationEnabled = partyInvitationEnabled;
    }

    @Override
    public boolean isChatEnabled() {
        return chatEnabled;
    }

    @Override
    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    @Override
    public boolean isParticleEnabled() {
        return particleEnabled;
    }

    @Override
    public void setParticleEnabled(boolean particleEnabled) {
        this.particleEnabled = particleEnabled;
    }

    @Override
    public boolean isPmEnabled() {
        return pmEnabled;
    }

    @Override
    public void setPmEnabled(boolean pmEnabled) {
        this.pmEnabled = pmEnabled;
        DogeCore.getInstance().getPacketHandler().writePacket(player, new PlayerSynchronizeBungeeOptionsPacket(
                player.getUniqueId(), pmEnabled, partyInvitationEnabled));
    }

    @Override
    public boolean isPartyInvitationEnabled() {
        return partyInvitationEnabled;
    }

    @Override
    public void setPartyInvitationEnabled(boolean partyInvitationEnabled) {
        this.partyInvitationEnabled = partyInvitationEnabled;
        DogeCore.getInstance().getPacketHandler().writePacket(player, new PlayerSynchronizeBungeeOptionsPacket(
                player.getUniqueId(), pmEnabled, partyInvitationEnabled));
    }

    /**
     * Update player's options from {@link PlayerSynchronizeBungeeOptionsPacket}.
     *
     * @param pmEnabled              If the player enable private message.
     * @param partyInvitationEnabled If the player enable party invitation.
     */
    public void update(boolean pmEnabled, boolean partyInvitationEnabled) {
        this.pmEnabled = pmEnabled;
        this.partyInvitationEnabled = partyInvitationEnabled;
    }
}
