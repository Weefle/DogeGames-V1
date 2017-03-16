package net.dogegames.dogecore.api.player;

public interface DogePlayerOptions {
    /**
     * @return If the player enable the chat.
     */
    boolean isChatEnabled();

    /**
     * Set if the player enable the chat.
     *
     * @param chatEnabled If the player enable the chat.
     */
    void setChatEnabled(boolean chatEnabled);

    /**
     * @return If the player enable particle.
     */
    boolean isParticleEnabled();

    /**
     * Set if the player enable particle.
     *
     * @param particleEnabled If the player enable particle.
     */
    void setParticleEnabled(boolean particleEnabled);

    /**
     * @return If the player enable private message.
     */
    boolean isPmEnabled();

    /**
     * Set if the player enable private message.
     *
     * @param pmEnabled If the player enable private message.
     */
    void setPmEnabled(boolean pmEnabled);

    /**
     * @return If the player enable party invitation.
     */
    boolean isPartyInvitationEnabled();

    /**
     * Set if the player enable party invitation.
     *
     * @param partyInvitationEnabled If the player enable party invitation.
     */
    void setPartyInvitationEnabled(boolean partyInvitationEnabled);
}
