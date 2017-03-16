package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.permission.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class KickCommand extends DogeBungeeCommand implements TabExecutor {
    public KickCommand() {
        super("kick", Rank.ADMIN, "getout");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            ProxiedPlayer target = input.getPlayerArg(0);
            if (target.equals(input.getPlayer())) {
                throw new CommandException("Vous ne pouvez pas vous expulser du serveur.");
            }
            DogeBungeePlayer targetEntity = DogeBungee.getInstance().getPlayerManager().getPlayer(target);

            if (targetEntity.getRank().getPower() > input.getEntity().getRank().getPower()) {
                throw new CommandException("Vous ne pouvez pas expulser ce joueur.");
            }

            // Kick the player
            String message = "§cVous venez d'être expulsé du serveur par " + input.getPlayer().getName() + ".";
            if (input.size() > 1) message += "\nRaison : " + input.getAll(1);
            target.disconnect(TextComponent.fromLegacyText(message));

            // Send confirmation
            input.getPlayer().sendMessage(TextComponent.fromLegacyText("§f[§bKick§f] §aVous venez d'expulser " + target.getName() + " du serveur."));
        } else {
            throw new CommandInvalidUsageException("/kick <joueur> [raison]");
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> matches = new HashSet<>();
        if (args.length == 1 && args[0].length() > 1) {
            String search = args[0].toLowerCase();
            ProxyServer.getInstance().getPlayers().stream()
                    .filter(player -> player.getName().toLowerCase().startsWith(search))
                    .forEach(player -> matches.add(player.getName()));
        }
        return matches;
    }
}
