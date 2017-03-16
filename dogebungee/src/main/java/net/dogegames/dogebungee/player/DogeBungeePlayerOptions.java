package net.dogegames.dogebungee.player;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

@Embedded
public class DogeBungeePlayerOptions {
    @Property("chat_enabled")
    private boolean chatEnabled = true;
    @Property("particle_enabled")
    private boolean particleEnabled = true;
    @Property("pm_enabled")
    private boolean pmEnabled = true;
    @Property("party_invitation_enabled")
    private boolean partyInvitationEnabled = true;

    DogeBungeePlayerOptions() {
    }

    /**
     * @return If the player enable the chat.
     */
    public boolean isChatEnabled() {
        return chatEnabled;
    }

    /**
     * Set if the player enable the chat.
     *
     * @param chatEnabled If the player enable the chat.
     */
    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    /**
     * @return If the player enable particle.
     */
    public boolean isParticleEnabled() {
        return particleEnabled;
    }

    /**
     * Set if the player enable particle.
     *
     * @param particleEnabled If the player enable particle.
     */
    public void setParticleEnabled(boolean particleEnabled) {
        this.particleEnabled = particleEnabled;
    }

    /**
     * @return If the player enable private message.
     */
    public boolean isPmEnabled() {
        return pmEnabled;
    }

    /**
     * Set if the player enable private message.
     *
     * @param pmEnabled If the player enable private message.
     */
    public void setPmEnabled(boolean pmEnabled) {
        this.pmEnabled = pmEnabled;
    }

    /**
     * @return If the player enable party invitation.
     */
    public boolean isPartyInvitationEnabled() {
        return partyInvitationEnabled;
    }

    /**
     * Set if the player enable party invitation.
     *
     * @param partyInvitationEnabled If the player enable party invitation.
     */
    public void setPartyInvitationEnabled(boolean partyInvitationEnabled) {
        this.partyInvitationEnabled = partyInvitationEnabled;
    }
}
