package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.player.permission.Rank;
import net.dogegames.dogeprotocol.packet.player.PlayerSynchronizeRankPacket;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class SetRankCommand extends DogeBungeeCommand implements TabExecutor {
    public SetRankCommand() {
        super("setrank", Rank.ADMIN, "setrk");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 1) {
            ProxiedPlayer target = input.getPlayerArg(0);
            try {
                Rank rank = Rank.valueOf(input.getArg(1).toUpperCase());

                DogeBungee.getInstance().getProtocolManager().writePacket(new PlayerSynchronizeRankPacket(target.getUniqueId(), (byte) rank.ordinal()));
                input.getPlayer().sendMessage(TextComponent.fromLegacyText("§f[§bRang§f] §aVous venez de passer " + target + " " + rank.getName().toLowerCase() + "."));
            } catch (IllegalArgumentException e) {
                throw new CommandException("Le rang demandé est introuvable.");
            }
        } else {
            throw new CommandInvalidUsageException("/setrank <joueur> <rang>");
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
