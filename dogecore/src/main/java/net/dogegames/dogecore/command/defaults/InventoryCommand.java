package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.api.permission.Rank;

public class InventoryCommand extends DogeCommand {
    public InventoryCommand() {
        super("inventory", Rank.ADMIN, "invsee", "inv");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            input.getPlayer().openInventory(input.getPlayerArg(0).getInventory());
        } else {
            throw new CommandInvalidUsageException("/inventory <joueur>");
        }
    }
}
