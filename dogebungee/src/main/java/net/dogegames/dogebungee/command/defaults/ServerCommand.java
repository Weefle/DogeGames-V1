package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.player.permission.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServerCommand extends DogeBungeeCommand implements TabExecutor {
    public ServerCommand() {
        super("server", Rank.DEVELOPER, "serv");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            if (!ProxyServer.getInstance().getServers().containsKey(input.getArg(0))) {
                throw new CommandException("Impossible de trouver le serveur demandé.");
            }
            ServerInfo server = ProxyServer.getInstance().getServers().get(input.getArg(0));
            if (server.getName().equals(input.getPlayer().getServer().getInfo().getName())) {
                throw new CommandException("Vous êtes déjà connecté sur ce serveur.");
            }
            input.getPlayer().connect(server);
            input.getPlayer().sendMessage(TextComponent.fromLegacyText("§f[§bServeur§f] §aVous venez de vous téléporter au serveur " + server.getName() + "."));
        } else {
            input.getPlayer().sendMessage(TextComponent.fromLegacyText("§6Vous êtes actuellement connecté sur le serveur : §b" + input.getPlayer().getServer().getInfo().getName()));
            TextComponent message = new TextComponent("§6Voici la liste des serveurs disponible : ");
            Iterator<ServerInfo> iterator = ProxyServer.getInstance().getServers().values().iterator();
            while (iterator.hasNext()) {
                String serverName = iterator.next().getName();
                TextComponent server = new TextComponent("§a" + serverName);
                server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Rejoindre")));
                server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + serverName));
                message.addExtra(server);
                if (iterator.hasNext()) message.addExtra(new TextComponent("§7, "));
            }

            input.getPlayer().sendMessage(message);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> matches = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            ProxyServer.getInstance().getServers().values().stream()
                    .filter(server -> server.getName().toLowerCase().startsWith(search))
                    .forEach(server -> matches.add(server.getName()));
        }
        return matches;
    }
}
