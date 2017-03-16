package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.api.permission.Rank;

public class WeatherCommand extends DogeCommand {
    public WeatherCommand() {
        super("weather", Rank.ADMIN, "sky");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            if ("clear".equalsIgnoreCase(input.getArg(0))) {
                input.getPlayer().getWorld().setStorm(false);
                input.getPlayer().getWorld().setThundering(false);
                input.getPlayer().sendMessage("§f[§bMétéo§f] §aVous venez de mettre le beau temps.");
            } else if ("rain".equalsIgnoreCase(input.getArg(0))) {
                input.getPlayer().getWorld().setStorm(true);
                input.getPlayer().getWorld().setThundering(false);
                input.getPlayer().sendMessage("§f[§bMétéo§f] §aVous venez de mettre la pluie.");
            } else if ("thunder".equalsIgnoreCase(input.getArg(0))) {
                input.getPlayer().getWorld().setStorm(true);
                input.getPlayer().getWorld().setThundering(true);
                input.getPlayer().sendMessage("§f[§bMétéo§f] §aVous venez de mettre le mauvais temps.");
            } else {
                throw new CommandInvalidUsageException("/weather <clear/rain/thunder>");
            }
        } else {
            throw new CommandInvalidUsageException("/weather <clear/rain/thunder>");
        }
    }
}
