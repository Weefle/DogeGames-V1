package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.helper.DateHelper;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.DogeBungeePlayerSanction;
import net.dogegames.dogebungee.player.permission.Rank;
import net.dogegames.dogeprotocol.packet.player.PlayerSanctionPacket;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class BanCommand extends DogeBungeeCommand implements TabExecutor {
    public BanCommand() {
        super("ban", Rank.ADMIN);
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            ProxiedPlayer target = input.getPlayerArg(0);
            if (target.equals(input.getPlayer())) {
                throw new CommandException("Vous ne pouvez pas vous bannir du serveur.");
            }
            DogeBungeePlayer targetEntity = DogeBungee.getInstance().getPlayerManager().getPlayer(target);
            if (targetEntity.getRank().getPower() > input.getEntity().getRank().getPower()) {
                throw new CommandException("Vous ne pouvez pas bannir ce joueur.");
            }
            Calendar finish = input.size() > 1 ? DateHelper.parseDiffCalendar(input.getArg(1)) : null;

            input.getEntity().writeMessage(new PlayerSanctionPacket((byte) DogeBungeePlayerSanction.SanctionType.BAN.ordinal(),
                    target.getUniqueId(), input.size() > 2 ? input.getAll(2) : null,
                    finish != null ? finish.getTimeInMillis() : null, input.getPlayer().getUniqueId()));

            input.getPlayer().sendMessage(TextComponent.fromLegacyText("§f[§bBan§f] §aVous venez de bannir " + target.getName() + "."));
        } else {
            throw new CommandInvalidUsageException("/ban <joueur> [temps] [raison]");
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
