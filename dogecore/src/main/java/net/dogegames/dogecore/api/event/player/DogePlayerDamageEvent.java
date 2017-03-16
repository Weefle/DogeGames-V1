package net.dogegames.dogecore.api.event.player;

import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DogePlayerDamageEvent extends Event implements Cancellable {
    private static HandlerList handlers = new HandlerList();
    private DogePlayer victim;
    private DogePlayer damager;
    private boolean cancelled;

    public DogePlayerDamageEvent(DogePlayer victim, DogePlayer damager) {
        this.victim = victim;
        this.damager = damager;
    }

    /**
     * @return HandlerList.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return Victim.
     */
    public DogePlayer getVictim() {
        return victim;
    }

    /**
     * @return Damager.
     */
    public DogePlayer getDamager() {
        return damager;
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
