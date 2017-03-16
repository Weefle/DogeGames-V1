package net.dogegames.dogecore.api;

import net.dogegames.dogecore.api.command.DogeCommandManager;
import net.dogegames.dogecore.api.display.DogeDisplayManager;
import net.dogegames.dogecore.api.gui.DogeGuiManager;
import net.dogegames.dogecore.api.location.DogeLocationManager;
import net.dogegames.dogecore.api.nms.ProtocolAccess;
import net.dogegames.dogecore.api.player.DogePlayerManager;
import net.dogegames.dogecore.api.team.DogeTeamManager;

public class DogeAPI {
    private static Impl impl;

    /**
     * @return The {@link DogePlayerManager}.
     */
    public static DogePlayerManager getPlayerManager() {
        return impl.getPlayerManager();
    }

    /**
     * @return The {@link DogeGuiManager}.
     */
    public static DogeGuiManager getGuiManager() {
        return impl.getGuiManager();
    }

    /**
     * @return The {@link ProtocolAccess}.
     */
    public static ProtocolAccess getProtocolAccess() {
        return impl.getProtocolAccess();
    }

    /**
     * @return The {@link DogeCommandManager}.
     */
    public static DogeCommandManager getCommandManager() {
        return impl.getCommandManager();
    }

    /**
     * @return The {@link DogeLocationManager}.
     */
    public static DogeLocationManager getLocationManager() {
        return impl.getLocationManager();
    }

    /**
     * @return The {@link DogeTeamManager}.
     */
    public static DogeTeamManager getTeamManager() {
        return impl.getTeamManager();
    }

    /**
     * @return The {@link DogeDisplayManager}.
     */
    public static DogeDisplayManager getDisplayManager() {
        return impl.getDisplayManager();
    }

    /**
     * Define the {@link DogeAPI} implementation.
     *
     * @param impl Implementation.
     */
    public static void setImplementation(Impl impl) {
        DogeAPI.impl = impl;
    }

    /**
     * Interface who provide a bridge with the {@link DogeAPI}.
     */
    public interface Impl {
        /**
         * @return The {@link DogePlayerManager}.
         */
        DogePlayerManager getPlayerManager();

        /**
         * @return The {@link DogeGuiManager}.
         */
        DogeGuiManager getGuiManager();

        /**
         * @return The {@link ProtocolAccess}.
         */
        ProtocolAccess getProtocolAccess();

        /**
         * @return The {@link DogeCommandManager}.
         */
        DogeCommandManager getCommandManager();

        /**
         * @return The {@link DogeLocationManager}.
         */
        DogeLocationManager getLocationManager();

        /**
         * @return The {@link DogeTeamManager}.
         */
        DogeTeamManager getTeamManager();

        /**
         * @return The {@link DogeDisplayManager}.
         */
        DogeDisplayManager getDisplayManager();
    }
}
