package net.dogegames.dogebungee.command;

import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.helper.DateHelper;
import net.dogegames.dogebungee.helper.TextHelper;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Calendar;

public class DogeBungeeCommandInput {
    private ProxiedPlayer player;
    private DogeBungeePlayer entity;
    private String command;
    private String[] args;

    /**
     * @param player  Sender ({@link ProxiedPlayer}).
     * @param entity  Sender's data ({@link DogeBungeePlayer}).
     * @param command Alias used.
     * @param args    Arguments.
     */
    DogeBungeeCommandInput(ProxiedPlayer player, DogeBungeePlayer entity, String command, String[] args) {
        this.player = player;
        this.entity = entity;
        this.command = command;
        this.args = args;
    }

    /**
     * @return Sender ({@link ProxiedPlayer}).
     */
    public ProxiedPlayer getPlayer() {
        return player;
    }

    /**
     * @return Sender's data ({@link DogeBungeePlayer}).
     */
    public DogeBungeePlayer getEntity() {
        return entity;
    }

    /**
     * @return Alias used.
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return Arguments.
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * @param i Argument.
     * @return Value.
     */
    public String getArg(int i) {
        return args[i];
    }

    /**
     * @param i Argument.
     * @return An integer.
     * @throws CommandException If there an exception when convert the string to an integer.
     */
    public int getIntArg(int i) throws CommandException {
        try {
            return Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[i] + " n'est pas un chiffre !");
        }
    }

    /**
     * @param i Argument.
     * @return The boolean.
     */
    public boolean getBoolArg(int i) {
        return Boolean.parseBoolean(args[i]);
    }

    /**
     * @param startAt Start at.
     * @return Arguments concatted.
     */
    public String getAll(int startAt) {
        return TextHelper.concat(args, startAt);
    }

    /**
     * @return Arguments concatted.
     */
    public String getAll() {
        return getAll(0);
    }

    /**
     * @param i Argument.
     * @return The {@link ProxiedPlayer}.
     * @throws CommandException If the player is not found.
     */
    public ProxiedPlayer getPlayerArg(int i) throws CommandException {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[i]);
        if (player == null) throw new CommandException("Le joueur " + args[i] + " n'est pas connecté !");
        return player;
    }

    /**
     * @param i Argument.
     * @return The {@link Calendar}.
     * @throws CommandException If there is an exception.
     */
    public Calendar getCalendarArg(int i) throws CommandException {
        Calendar calendar = DateHelper.parseDiffCalendar(args[i]);
        if (calendar == null) throw new CommandException("La date donnée n'est pas valide !");
        return calendar;
    }

    /**
     * @return Size of argument array.
     */
    public int size() {
        return args.length;
    }
}
