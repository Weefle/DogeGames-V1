package net.dogegames.dogecore.api.command;

import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.permission.Rank;

public abstract class DogeCommand {
    private String command;
    private Rank rank;
    private String[] aliases;

    /**
     * @param command Command's name.
     * @param rank    Rank required to use this command.
     * @param aliases Command's alias.
     */
    public DogeCommand(String command, Rank rank, String... aliases) {
        this.command = command;
        this.rank = rank;
        this.aliases = aliases;
    }

    /**
     * @return Command's name.
     */
    public final String getCommand() {
        return command;
    }

    /**
     * @return Rank required to use this command.
     */
    public final Rank getRank() {
        return rank;
    }

    /**
     * @return Command's alias.
     */
    public final String[] getAliases() {
        return aliases;
    }

    /**
     * Run the command.
     *
     * @param input The command input.
     * @throws CommandException If there is a command exception like player not found...
     */
    public abstract void run(DogeCommandInput input) throws CommandException;
}
