package net.dogegames.dogecore.api.command;

import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.entity.Player;

import java.util.Calendar;

public interface DogeCommandInput {
    /**
     * @return The player (sender).
     */
    Player getPlayer();

    /**
     * @return Player's data.
     */
    DogePlayer getEntity();

    /**
     * @return Command label.
     */
    String getCommand();

    /**
     * @return Arguments.
     */
    String[] getArgs();

    /**
     * @param i Argument.
     * @return Value.
     */
    String getArg(int i);

    /**
     * @param i Argument.
     * @return An integer.
     * @throws CommandException If there an exception when convert the string to an integer.
     */
    int getIntArg(int i) throws CommandException;

    /**
     * @param i Argument.
     * @return The boolean.
     */
    boolean getBoolArg(int i);

    /**
     * @param startAt Start at.
     * @return Arguments concatted.
     */
    String getAll(int startAt);

    /**
     * @return Arguments concatted.
     */
    String getAll();

    /**
     * @param i Argument.
     * @return The player.
     * @throws CommandException If the player is not found.
     */
    Player getPlayerArg(int i) throws CommandException;

    /**
     * @param i Argument.
     * @return The {@link Calendar}.
     * @throws CommandException If there is an exception.
     */
    Calendar getCalendarArg(int i) throws CommandException;

    /**
     * @return Size of argument array.
     */
    int size();
}
