package net.dogegames.dogecore.api.event.player;

import net.dogegames.dogecore.api.player.DogePlayer;
import net.dogegames.dogecore.api.team.DogeTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DogePlayerQuitTeamEvent extends Event {
    private static HandlerList handlers = new HandlerList();
    private DogePlayer player;
    private DogeTeam team;

    public DogePlayerQuitTeamEvent(DogePlayer player, DogeTeam team) {
        this.player = player;
        this.team = team;
    }

    /**
     * @return HandlerList.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return Player.
     */
    public DogePlayer getPlayer() {
        return player;
    }

    /**
     * @return Team.
     */
    public DogeTeam getTeam() {
        return team;
    }

    /**
     * @return HandlerList.
     */
    public HandlerList getHandlers() {
        return handlers;
    }
}
