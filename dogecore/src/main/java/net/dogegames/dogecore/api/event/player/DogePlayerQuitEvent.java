package net.dogegames.dogecore.api.event.player;

import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DogePlayerQuitEvent extends Event {
    private static HandlerList handlers = new HandlerList();
    private DogePlayer player;
    private String quitMessage;

    public DogePlayerQuitEvent(DogePlayer player) {
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
     * @return Quit message.
     */
    public String getQuitMessage() {
        return quitMessage;
    }

    /**
     * Set the quit message.
     *
     * @param quitMessage Quit message.
     */
    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    /**
     * @return HandlerList.
     */
    public HandlerList getHandlers() {
        return handlers;
    }
}
