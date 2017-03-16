package net.dogegames.dogecore.api.command;

public interface DogeCommandManager {
    /**
     * Register a command.
     *
     * @param command The {@link DogeCommand}.
     */
    void registerCommand(DogeCommand command);

    /**
     * Unregister a command.
     *
     * @param command The command name.
     */
    void unregisterCommand(String command);
}
