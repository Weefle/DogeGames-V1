package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.helper.DateHelper;
import net.dogegames.dogebungee.helper.TextHelper;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.permission.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class InfoCommand extends DogeBungeeCommand implements TabExecutor {
    public InfoCommand() {
        super("info", Rank.ADMIN, "informations", "information", "you", "hack");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            DogeBungeePlayer targetEntity;
            try {
                targetEntity = DogeBungee.getInstance().getPlayerManager().getPlayer(input.getPlayerArg(0));
            } catch (CommandException e) {
                targetEntity = DogeBungee.getInstance().getDatastore().find(DogeBungeePlayer.class).field("username").equalIgnoreCase(input.getArg(0)).get();
            }
            if (targetEntity == null) throw new CommandException("Ce joueur ne s'est jamais connecté.");

            input.getPlayer().sendMessage(new TextComponent(ChatColor.GOLD + TextHelper.separator()));
            input.getPlayer().sendMessage(new TextComponent("§aPseudo du joueur : §b" + targetEntity.getUsername()));
            input.getPlayer().sendMessage(new TextComponent("§aInscrit depuis le : §b" + DateHelper.UNTIL_FORMAT.format(targetEntity.getFirstConnection())));
            input.getPlayer().sendMessage(new TextComponent("§aRang du joueur : §b" + targetEntity.getRank().getName()));
            input.getPlayer().sendMessage(new TextComponent("§aDogeCoins du joueur : §b" + targetEntity.getDogeCoins()));
            input.getPlayer().sendMessage(new TextComponent("§aShiba du joueur : §b" + targetEntity.getShiba()));
            input.getPlayer().sendMessage(new TextComponent("§aBanni : §b" + (targetEntity.getBan() == null ? "non" : targetEntity.getBan().tlFinish())));
            input.getPlayer().sendMessage(new TextComponent("§aMute : §b" + (targetEntity.getMute() == null ? "non" : targetEntity.getMute().tlFinish())));
            input.getPlayer().sendMessage(new TextComponent(ChatColor.GOLD + TextHelper.separator()));
        } else {
            throw new CommandInvalidUsageException("/info <joueur>");
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
