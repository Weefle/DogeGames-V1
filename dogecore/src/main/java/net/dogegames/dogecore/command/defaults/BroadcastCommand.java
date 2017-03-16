package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.api.permission.Rank;
import org.bukkit.Bukkit;

public class BroadcastCommand extends DogeCommand {
    public BroadcastCommand() {
        super("broadcast", Rank.ADMIN, "bc", "say");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            Bukkit.broadcastMessage("§f[§bMessage§f] §a" + input.getAll());
        } else {
            throw new CommandInvalidUsageException("/broadcast <message>");
        }
    }
}
