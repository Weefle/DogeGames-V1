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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Set;

public class MessageCommand extends DogeBungeeCommand implements TabExecutor {
    public MessageCommand() {
        super("msg", Rank.PLAYER, "m", "w", "whisper", "t", "tell", "pm");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 1) {
            ProxiedPlayer target = input.getPlayerArg(0);
            if (target.equals(input.getPlayer())) {
                throw new CommandException("Vous ne pouvez pas vous envoyer de message.");
            }
            DogeBungeePlayer targetEntity = DogeBungee.getInstance().getPlayerManager().getPlayer(target);
            if (targetEntity.getOptions().isPmEnabled()) {
                TextComponent textComponent = new TextComponent(" §7" + input.getAll(1));

                // Send message to the target
                TextComponent targetComponent = new TextComponent("§f[" + input.getEntity().getRank().getColor() + input.getEntity().getRank().getName() + " " + input.getPlayer().getName() + " §f-> " + targetEntity.getRank().getColor() + "Moi§f]");
                targetComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Répondre à " + input.getPlayer().getName())));
                targetComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + input.getPlayer().getName() + " "));
                targetComponent.addExtra(textComponent);
                target.sendMessage(targetComponent);

                // Send message to the sender
                TextComponent senderComponent = new TextComponent("§f[" + input.getEntity().getRank().getColor() + "Moi §f-> " + targetEntity.getRank().getColor() + targetEntity.getRank().getName() + " " + target.getName() + "§f]");
                senderComponent.addExtra(textComponent);
                input.getPlayer().sendMessage(senderComponent);
            } else {
                input.getPlayer().sendMessage(TextComponent.fromLegacyText("Erreur : " + target.getName() + " n'autorise pas les messages privés."));
            }
        } else {
            throw new CommandInvalidUsageException("/msg <joueur> <message>");
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
