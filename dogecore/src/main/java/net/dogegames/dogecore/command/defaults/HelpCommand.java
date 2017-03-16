package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.helper.TextHelper;
import net.dogegames.dogecore.api.permission.Rank;
import net.md_5.bungee.api.ChatColor;

public class HelpCommand extends DogeCommand {
    public HelpCommand() {
        super("help", Rank.PLAYER, "?");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        input.getPlayer().sendMessage(ChatColor.GOLD + TextHelper.separator());
        input.getPlayer().sendMessage("§6/friends §8- §7Gérer ses amis");
        input.getPlayer().sendMessage("§6/group §8- §7Gérer son groupe");
        input.getPlayer().sendMessage("§6/lobby §8- §7Retourner au lobby");
        input.getPlayer().sendMessage("§6/help §8- §7Obtenir de l'aide");
        input.getPlayer().sendMessage("§6/msg §8- §7Envoyer un message");
        if (input.getEntity().getRank().getPower() >= Rank.HELPER.getPower()) {
            input.getPlayer().sendMessage("§6/staff §8- §7Accéder au canal du staff");
            input.getPlayer().sendMessage("§6/sanction §8- §7Sanctionner un joueur");
        }
        input.getPlayer().sendMessage(ChatColor.GOLD + TextHelper.separator());
    }
}
