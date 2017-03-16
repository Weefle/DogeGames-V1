package net.dogegames.dogebungee.player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.player.permission.Rank;
import net.dogegames.dogeprotocol.bungee.BungeePacketManager;
import net.dogegames.dogeprotocol.packet.DogePacket;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizePacket;
import net.md_5.bungee.api.ProxyServer;
import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(value = "players", noClassnameStored = true)
public class DogeBungeePlayer {
    @Id
    private UUID uniqueId;
    @Property("username")
    private String username;
    @Property("rank")
    private Rank rank;
    @Property("doge_coins")
    private double dogeCoins;
    @Property("shiba")
    private double shiba;
    @Property("first_connection")
    private Date firstConnection;
    @Property("last_connection")
    private Date lastConnection;
    @Embedded("options")
    private DogeBungeePlayerOptions options;
    @Reference(idOnly = true, value = "friends")
    private Set<DogeBungeePlayer> friends;
    @Embedded("sanctions")
    private Set<DogeBungeePlayerSanction> sanctions;
    // Non persistent fields
    private boolean usingStaffChat;

    DogeBungeePlayer(UUID uniqueId, String username) {
        this();
        this.uniqueId = uniqueId;
        this.username = username;
        this.rank = Rank.PLAYER;
        this.firstConnection = new Date();
        this.options = new DogeBungeePlayerOptions();
    }

    private DogeBungeePlayer() {
        this.friends = new HashSet<>();
        this.sanctions = new HashSet<>();
    }

    /**
     * @return The mojang unique id ({@link UUID}).
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * @return Player's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set player's username.
     *
     * @param username Player's username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Player's {@link Rank}.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Set player's rank.
     *
     * @param rank Player's {@link Rank}.
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * @return Player's dogecoins.
     */
    public double getDogeCoins() {
        return dogeCoins;
    }

    /**
     * Set player's dogecoins.
     *
     * @param dogeCoins Player's dogecoins.
     */
    public void setDogeCoins(double dogeCoins) {
        this.dogeCoins = dogeCoins;
    }

    /**
     * @return Player's shiba.
     */
    public double getShiba() {
        return shiba;
    }

    /**
     * Set player's shiba.
     *
     * @param shiba Player's shiba.
     */
    public void setShiba(double shiba) {
        this.shiba = shiba;
    }

    /**
     * @return Player's first connection.
     */
    public Date getFirstConnection() {
        return firstConnection;
    }

    /**
     * @return Player's last connection.
     */
    public Date getLastConnection() {
        return lastConnection;
    }

    /**
     * Set player's last connection.
     *
     * @param lastConnection Player's last connection.
     */
    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }

    /**
     * @return Player's options ({@link DogeBungeePlayerOptions}).
     */
    public DogeBungeePlayerOptions getOptions() {
        return options;
    }

    /**
     * @return Player's friends.
     */
    public Set<DogeBungeePlayer> getFriends() {
        return friends;
    }

    /**
     * @return Player's sanctions (set of {@link DogeBungeePlayerSanction}).
     */
    public Set<DogeBungeePlayerSanction> getSanctions() {
        return sanctions;
    }

    /**
     * @return If the player is in staff chat mode.
     */
    public boolean isUsingStaffChat() {
        return usingStaffChat;
    }

    /**
     * Set if the player is in staff chat mode.
     *
     * @param usingStaffChat If the player is in staff chat mode.
     */
    public void setUsingStaffChat(boolean usingStaffChat) {
        this.usingStaffChat = usingStaffChat;
    }

    /**
     * @return Return the {@link DogeBungeePlayerSanction} if the player is banned.
     */
    public DogeBungeePlayerSanction getBan() {
        for (DogeBungeePlayerSanction sanction : sanctions) {
            if (sanction.getType() == DogeBungeePlayerSanction.SanctionType.BAN) {
                if ((sanction.getFinish() == null || sanction.getFinish().getTime() > System.currentTimeMillis()) && sanction.getCanceller() == null) {
                    return sanction;
                }
            }
        }
        return null;
    }

    /**
     * @return Return the {@link DogeBungeePlayerSanction} if the player is mutted.
     */
    public DogeBungeePlayerSanction getMute() {
        for (DogeBungeePlayerSanction sanction : sanctions) {
            if (sanction.getType() == DogeBungeePlayerSanction.SanctionType.MUTE) {
                if (sanction.getFinish().getTime() > System.currentTimeMillis() && sanction.getCanceller() == null) {
                    return sanction;
                }
            }
        }
        return null;
    }

    /**
     * @return The {@link PlayerSynchronizePacket}.
     */
    public PlayerSynchronizePacket getSynchronizePacket() {
        return new PlayerSynchronizePacket(uniqueId, username, (byte) rank.ordinal(), dogeCoins, shiba,
                options.isChatEnabled(), options.isParticleEnabled(), options.isPmEnabled(), options.isPartyInvitationEnabled());
    }

    /**
     * Send a packet on the plugin messaging.
     *
     * @param packet The packet.
     */
    public void writeMessage(DogePacket packet) {
        try {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            BungeePacketManager.writePacket(output, packet);
            ProxyServer.getInstance().getPlayer(uniqueId).getServer().getInfo().sendData("DogeProtocol", output.toByteArray());
        } catch (Exception e) {
            DogeBungee.getInstance().getLogger().severe("Error while writing packet: " + e.getMessage());
        }
    }
}
