package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.permission.Rank;
import org.bukkit.entity.Player;

public class HealCommand extends DogeCommand {
    public HealCommand() {
        super("heal", Rank.ADMIN, "feed", "regen");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        Player target = input.size() > 0 ? input.getPlayerArg(0) : input.getPlayer();
        target.setHealth(20);
        target.setFoodLevel(20);
        if (input.getPlayer().equals(target)) {
            input.getPlayer().sendMessage("§f[§bHeal§f] §aVous venez de vous régénérer.");
        } else {
            input.getPlayer().sendMessage("§f[§bHeal§f] §aVous venez de régénérer §e" + target.getName() + "§a.");
            target.sendMessage("§f[§bHeal§f] §aVous venez d'être régénéré.");
        }
    }
}
