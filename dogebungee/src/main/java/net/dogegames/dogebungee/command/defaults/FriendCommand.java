package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.helper.TextHelper;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.permission.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FriendCommand extends DogeBungeeCommand implements TabExecutor {
    public FriendCommand() {
        super("friend", Rank.PLAYER, "friends", "f", "ami", "amis", "a");
    }

    /**
     * Send friend list to the player.
     *
     * @param player      The player.
     * @param sendOffline If you want send offline friends.
     */
    public static void sendFriendList(ProxiedPlayer player, boolean sendOffline) {
        DogeBungeePlayer entity = DogeBungee.getInstance().getPlayerManager().getPlayer(player);

        Set<String> onlineFriends = new HashSet<>();
        Set<String> offlineFriends = new HashSet<>();

        entity.getFriends().forEach(friend -> {
            if (ProxyServer.getInstance().getPlayer(friend.getUniqueId()) != null)
                onlineFriends.add(friend.getUsername());
            else offlineFriends.add(friend.getUsername());
        });

        // Send the separator
        player.sendMessage(TextComponent.fromLegacyText(ChatColor.GOLD + TextHelper.separator()));

        TextComponent onlineMessage = new TextComponent("§6Vos amis connectés : ");
        if (onlineFriends.size() == 0) {
            onlineMessage.addExtra("§7Aucun");
        } else {
            Iterator<String> friends = onlineFriends.iterator();
            while (friends.hasNext()) {
                String online = friends.next();
                TextComponent message = new TextComponent("§a" + online);
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Envoyer un message")));
                message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + online + " "));
                if (friends.hasNext()) message.addExtra(new TextComponent("§7, "));
                onlineMessage.addExtra(message);
            }
        }
        // Send online friends
        player.sendMessage(onlineMessage);

        if (sendOffline) {
            TextComponent offlineMessage = new TextComponent("§6Vos amis déconnectés : ");
            if (offlineFriends.size() == 0) {
                offlineMessage.addExtra("§7Aucun");
            } else {
                Iterator<String> friends = offlineFriends.iterator();
                while (friends.hasNext()) {
                    String offline = friends.next();
                    TextComponent message = new TextComponent("§c" + offline);
                    if (friends.hasNext()) message.addExtra(new TextComponent("§7, "));
                    offlineMessage.addExtra(message);
                }
            }
            // Send offline friends
            player.sendMessage(offlineMessage);
        }

        // Send the separator
        player.sendMessage(TextComponent.fromLegacyText(ChatColor.GOLD + TextHelper.separator()));
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            if (input.getArg(0).equalsIgnoreCase("help")) {
                input.getPlayer().sendMessage(TextComponent.fromLegacyText(ChatColor.GOLD + TextHelper.separator()));
                input.getPlayer().sendMessage(TextComponent.fromLegacyText("§6/friend help §8- §7Obtenir de l'aide"));
                input.getPlayer().sendMessage(TextComponent.fromLegacyText("§6/friend add <joueur> §8- §7Ajouter un ami"));
                input.getPlayer().sendMessage(TextComponent.fromLegacyText("§6/friend remove <joueur> §8- §7Supprimer un ami"));
                input.getPlayer().sendMessage(TextComponent.fromLegacyText("§6/friend list §8- §7Lister ses amis"));
                input.getPlayer().sendMessage(TextComponent.fromLegacyText(ChatColor.GOLD + TextHelper.separator()));
            } else if (input.getArg(0).equalsIgnoreCase("list")) {
                sendFriendList(input.getPlayer(), true);
            } else if (input.getArg(0).equalsIgnoreCase("add") && input.size() > 1) {
                ProxiedPlayer target = input.getPlayerArg(1);
                if (input.getPlayer().equals(target)) {
                    throw new CommandException("Vous ne pouvez pas vous ajouter en ami.");
                }
                DogeBungeePlayer targetEntity = DogeBungee.getInstance().getPlayerManager().getPlayer(target);
                if (input.getEntity().getFriends().contains(targetEntity)) {
                    input.getPlayer().sendMessage(TextComponent.fromLegacyText("Erreur : Vous êtes déjà ami avec " + target.getName() + "."));
                    return;
                }
                input.getEntity().getFriends().add(targetEntity);
                input.getPlayer().sendMessage(TextComponent.fromLegacyText("§aVous maintenant ami avec " + target.getName() + " !"));
            } else if ((input.getArg(0).equalsIgnoreCase("remove") || input.getArg(0).equalsIgnoreCase("delete")) && input.size() > 1) {
                ProxiedPlayer target = input.getPlayerArg(1);
                for (DogeBungeePlayer friend : input.getEntity().getFriends()) {
                    if (friend.getUniqueId().equals(target.getUniqueId())) {
                        input.getEntity().getFriends().remove(friend);
                        input.getPlayer().sendMessage(TextComponent.fromLegacyText("§aVous venez de supprimer " + friend.getUsername() + " de vos amis."));
                        return;
                    }
                }
                throw new CommandException("§cVous n'êtes pas ami avec ce joueur.");
            } else {
                throw new CommandInvalidUsageException("/friend help");
            }
        } else {
            sendFriendList(input.getPlayer(), true);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> matches = new HashSet<>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            if ("help".startsWith(search)) matches.add("help");
            if ("list".startsWith(search)) matches.add("list");
            if ("add".startsWith(search)) matches.add("add");
            if ("remove".startsWith(search)) matches.add("remove");
            if ("delete".startsWith(search)) matches.add("delete");
        } else if (args.length == 2 && args[1].length() > 1) {
            String search = args[1].toLowerCase();
            ProxyServer.getInstance().getPlayers().stream()
                    .filter(player -> player.getName().toLowerCase().startsWith(search))
                    .forEach(player -> matches.add(player.getName()));
        }
        return matches;
    }
}
