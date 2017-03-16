package net.dogegames.dogecore.api.event.player;

import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DogePlayerJoinEvent extends Event {
    private static HandlerList handlers = new HandlerList();
    private DogePlayer player;
    private String joinMessage;

    public DogePlayerJoinEvent(DogePlayer player) {
        this.player = player;
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
     * @return Join message.
     */
    public String getJoinMessage() {
        return joinMessage;
    }

    /**
     * Set the join message.
     *
     * @param joinMessage Join message.
     */
    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    /**
     * @return HandlerList.
     */
    public HandlerList getHandlers() {
        return handlers;
    }
}
