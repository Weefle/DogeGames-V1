package net.dogegames.dogecore.api.player;

import net.dogegames.dogecore.api.data.DataHolder;
import net.dogegames.dogecore.api.display.DogeBar;
import net.dogegames.dogecore.api.display.DogeScoreboard;
import net.dogegames.dogecore.api.permission.Rank;
import net.dogegames.dogecore.api.team.DogeTeam;

import java.util.UUID;

public interface DogePlayer extends DataHolder {
    /**
     * @return The mojang unique id ({@link UUID}).
     */
    UUID getUniqueId();

    /**
     * @return Player's username.
     */
    String getUsername();

    /**
     * @return Player's {@link Rank}.
     */
    Rank getRank();

    /**
     * @return Player's dogecoins.
     */
    double getDogeCoins();

    /**
     * Set player's dogecoins.
     *
     * @param dogeCoins Player's dogecoins.
     */
    void setDogeCoins(double dogeCoins);

    /**
     * @return Player's shiba.
     */
    double getShiba();

    /**
     * Set player's shiba.
     *
     * @param shiba Player's shiba.
     */
    void setShiba(double shiba);

    /**
     * @return Player's options ({@link DogePlayerOptions}).
     */
    DogePlayerOptions getOptions();

    /**
     * @return Player's team.
     */
    DogeTeam getTeam();

    /**
     * Set the team.
     *
     * @param team Team.
     */
    void setTeam(DogeTeam team);

    /**
     * @return The bar.
     */
    DogeBar getBar();

    /**
     * @return The scoreboard.
     */
    DogeScoreboard getScoreboard();
}
