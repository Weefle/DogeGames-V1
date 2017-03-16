package net.dogegames.dogecore.api.display;

import net.dogegames.dogecore.api.player.DogePlayer;

public interface DogeDisplay {
    /**
     * Call when player need a display update.
     *
     * @param player     Player.
     * @param bar        Player's bar.
     * @param scoreboard Player's scoreboard.
     */
    void display(DogePlayer player, DogeBar bar, DogeScoreboard scoreboard);
}
