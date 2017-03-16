package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.api.permission.Rank;

public class TimeCommand extends DogeCommand {
    public TimeCommand() {
        super("time", Rank.ADMIN);
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            int time;
            if (input.getArg(0).equalsIgnoreCase("day")) time = 0;
            else if (input.getArg(0).equalsIgnoreCase("day")) time = 12500;
            else time = input.getIntArg(0);

            input.getPlayer().getWorld().setTime(time);
            input.getPlayer().sendMessage("§f[§bTemps§f] §aLe temps a été mis à jour.");
        } else {
            throw new CommandInvalidUsageException("/time <valeur>");
        }
    }
}
