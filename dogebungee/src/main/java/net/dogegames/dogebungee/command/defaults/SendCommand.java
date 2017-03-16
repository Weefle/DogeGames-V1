package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.player.permission.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class SendCommand extends DogeBungeeCommand implements TabExecutor {
    public SendCommand() {
        super("send", Rank.ADMIN, "mv", "move");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 1) {
            ProxiedPlayer target = input.getPlayerArg(0);
            if (!ProxyServer.getInstance().getServers().containsKey(input.getArg(1))) {
                throw new CommandException("Impossible de trouver le serveur demandé.");
            }
            ServerInfo server = ProxyServer.getInstance().getServers().get(input.getArg(1));

            target.connect(server);
            target.sendMessage(TextComponent.fromLegacyText("§f[§bServeur§f] §aVous venez d'être téléporté au serveur " + server.getName() + "."));
            input.getPlayer().sendMessage(TextComponent.fromLegacyText("§f[§bServeur§f] §aVous venez de téléporter " + target.getName() + " au serveur " + server.getName() + "."));
        } else {
            throw new CommandInvalidUsageException("/send <joueur> <serveur>");
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
        } else if (args.length == 2) {
            String search = args[1].toLowerCase();
            ProxyServer.getInstance().getServers().values().stream()
                    .filter(server -> server.getName().toLowerCase().startsWith(search))
                    .forEach(server -> matches.add(server.getName()));
        }
        return matches;
    }
}
