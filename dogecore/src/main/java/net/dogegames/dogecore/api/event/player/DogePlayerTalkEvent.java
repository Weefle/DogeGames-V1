package net.dogegames.dogecore.api.event.player;

import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DogePlayerTalkEvent extends Event implements Cancellable {
    private static HandlerList handlers = new HandlerList();
    private DogePlayer player;
    private String format;
    private String message;
    private boolean cancelled;

    public DogePlayerTalkEvent(DogePlayer player, String format, String message) {
        this.player = player;
        this.format = format;
        this.message = message;
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
     * @return Format.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Set the format.
     *
     * @param format Format.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return Message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message.
     *
     * @param message Message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * @return HandlerList.
     */
    public HandlerList getHandlers() {
        return handlers;
    }
}
