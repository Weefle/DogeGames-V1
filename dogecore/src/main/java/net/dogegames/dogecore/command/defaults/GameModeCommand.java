package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.api.permission.Rank;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeCommand extends DogeCommand {
    public GameModeCommand() {
        super("gamemode", Rank.ADMIN, "gm");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            Player target = input.size() > 1 ? input.getPlayerArg(1) : input.getPlayer();
            GameMode mode = matchGamemode(input.getArg(0));
            if (mode == null) throw new CommandException("Mode de jeu invalide.");
            target.setGameMode(mode);
            if (input.getPlayer().equals(target)) {
                input.getPlayer().sendMessage("§f[§bGameMode§f] §aVous venez de mettre à jour votre mode de jeu.");
            } else {
                target.sendMessage("§f[§bGameMode§f] §aVotre mode de jeu à été mis à jour.");
                input.getPlayer().sendMessage("§f[§bGameMode§f] §aVous venez de mettre à jour le mode de jeu de " + target.getName() + ".");
            }
        } else {
            throw new CommandInvalidUsageException("/gamemode <mode> [joueur]");
        }
    }

    private GameMode matchGamemode(String g) {
        GameMode mode = null;
        if (g.equalsIgnoreCase("gmc") || g.contains("creat") || g.equalsIgnoreCase("1") || g.equalsIgnoreCase("c")) {
            mode = GameMode.CREATIVE;
        } else if (g.equalsIgnoreCase("gms") || g.contains("survi") || g.equalsIgnoreCase("0") || g.equalsIgnoreCase("s")) {
            mode = GameMode.SURVIVAL;
        } else if (g.equalsIgnoreCase("gma") || g.contains("advent") || g.equalsIgnoreCase("2") || g.equalsIgnoreCase("a")) {
            mode = GameMode.ADVENTURE;
        } else if (g.equalsIgnoreCase("gmsp") || g.contains("spec") || g.equalsIgnoreCase("3") || g.equalsIgnoreCase("sp")) {
            mode = GameMode.SPECTATOR;
        }
        return mode;
    }
}
