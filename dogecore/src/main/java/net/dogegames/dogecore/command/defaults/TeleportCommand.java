package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.api.permission.Rank;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportCommand extends DogeCommand {
    public TeleportCommand() {
        super("teleport", Rank.MODERATOR, "tp", "tele");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 2) {
            double x = getDoubleArg(input.getArg(0));
            double y = getDoubleArg(input.getArg(1));
            double z = getDoubleArg(input.getArg(2));

            input.getPlayer().teleport(new Location(input.getPlayer().getWorld(), x, y, z));
            input.getPlayer().sendMessage("§f[§bTP§f] §aVous venez de vous teleporter.");
        } else if (input.size() > 1) {
            Player player = input.getPlayerArg(0);
            Player target = input.getPlayerArg(1);
            player.teleport(target);

            if (input.getPlayer().equals(player)) {
                input.getPlayer().sendMessage("§f[§bTP§f] §aVous venez de vous teleporter à " + target.getName() + ".");
            } else {
                player.sendMessage("§f[§bTP§f] §aVous venez d'être teleporté à " + target.getName() + ".");
                input.getPlayer().sendMessage("§f[§bTP§f] §aVous venez de teleporter " + player.getName() + " à " + target.getName() + ".");
            }
        } else if (input.size() > 0) {
            Player target = input.getPlayerArg(0);
            input.getPlayer().teleport(target);
            input.getPlayer().sendMessage("§f[§bTP§f] §aVous venez de vous teleporter à " + target.getName() + ".");
        } else {
            throw new CommandInvalidUsageException("/teleport <joueur> <cible> ou /teleport <x> <y> <z>");
        }
    }

    private double getDoubleArg(String arg) throws CommandException {
        try {
            return Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            throw new CommandException(arg + " n'est pas un chiffre !");
        }
    }
}
