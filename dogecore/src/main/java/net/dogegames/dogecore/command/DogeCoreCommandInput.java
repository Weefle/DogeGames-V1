package net.dogegames.dogecore.command;

import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.helper.DateHelper;
import net.dogegames.dogecore.api.helper.TextHelper;
import net.dogegames.dogecore.player.DogeCorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class DogeCoreCommandInput implements DogeCommandInput {
    private Player player;
    private DogeCorePlayer entity;
    private String command;
    private String[] args;

    DogeCoreCommandInput(Player player, DogeCorePlayer entity, String command, String[] args) {
        this.player = player;
        this.entity = entity;
        this.command = command;
        this.args = args;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public DogeCorePlayer getEntity() {
        return entity;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public String getArg(int i) {
        return args[i];
    }

    @Override
    public int getIntArg(int i) throws CommandException {
        try {
            return Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
            throw new CommandException(args[i] + " n'est pas un chiffre !");
        }
    }

    @Override
    public boolean getBoolArg(int i) {
        return Boolean.parseBoolean(args[i]);
    }

    @Override
    public String getAll(int startAt) {
        return TextHelper.concat(args, startAt);
    }

    @Override
    public String getAll() {
        return getAll(0);
    }

    @Override
    public Player getPlayerArg(int i) throws CommandException {
        Player player = Bukkit.getPlayer(args[i]);
        if (player == null) throw new CommandException("Le joueur " + args[i] + " n'est pas connecté !");
        return player;
    }

    @Override
    public Calendar getCalendarArg(int i) throws CommandException {
        Calendar calendar = DateHelper.parseDiffCalendar(args[i]);
        if (calendar == null) throw new CommandException("La date donnée n'est pas valide !");
        return calendar;
    }

    @Override
    public int size() {
        return args.length;
    }
}
