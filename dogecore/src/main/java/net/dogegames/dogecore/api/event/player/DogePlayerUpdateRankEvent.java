package net.dogegames.dogecore.api.event.player;

import net.dogegames.dogecore.api.permission.Rank;
import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DogePlayerUpdateRankEvent extends Event {
    private static HandlerList handlers = new HandlerList();
    private DogePlayer player;
    private Rank from;
    private Rank to;

    public DogePlayerUpdateRankEvent(DogePlayer player, Rank from, Rank to) {
        this.player = player;
        this.from = from;
        this.to = to;
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
     * @return The rank from.
     */
    public Rank getFrom() {
        return from;
    }

    /**
     * @return The rank to.
     */
    public Rank getTo() {
        return to;
    }

    /**
     * @return HandlerList.
     */
    public HandlerList getHandlers() {
        return handlers;
    }
}
