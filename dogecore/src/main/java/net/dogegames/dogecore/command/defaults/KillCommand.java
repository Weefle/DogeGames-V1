package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.permission.Rank;
import org.bukkit.entity.Player;

public class KillCommand extends DogeCommand {
    public KillCommand() {
        super("kill", Rank.ADMIN);
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        Player player = input.size() > 0 ? input.getPlayerArg(0) : input.getPlayer();
        if (input.getPlayer().equals(player)) input.getPlayer().sendMessage("§f[§bKill§f] §aVous venez de vous tuer.");
        else input.getPlayer().sendMessage("§f[§bKill§f] §aVous venez de tuer " + player.getName() + ".");
    }
}
