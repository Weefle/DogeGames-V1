package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.permission.Rank;
import org.bukkit.entity.Player;

public class ClearCommand extends DogeCommand {
    public ClearCommand() {
        super("clear", Rank.ADMIN, "clean", "clearinventory", "ci");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        Player player = input.size() > 0 ? input.getPlayerArg(0) : input.getPlayer();
        player.getInventory().clear();

        if (input.getPlayer().equals(player)) {
            input.getPlayer().sendMessage("§f[§bClear§f] §aVous venez de vider votre inventaire.");
        } else {
            player.sendMessage("§f[§bClear§f] §aVotre inventaire vient d'être vidé.");
            input.getPlayer().sendMessage("§f[§bClear§f] §aVous venez de vider l'inventaire de " + player.getName() + ".");
        }
    }
}
