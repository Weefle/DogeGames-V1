package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.api.permission.Rank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Iterator;

public class WorldCommand extends DogeCommand {
    public WorldCommand() {
        super("world", Rank.ADMIN);
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            if (input.getArg(0).equalsIgnoreCase("list") || input.getArg(0).equalsIgnoreCase("ls")) {
                input.getPlayer().sendMessage("§6Vous êtes actuellement sur le monde : §b" + input.getPlayer().getWorld().getName());
                TextComponent message = new TextComponent("§6Voici la liste des serveurs disponible : ");
                Iterator<World> iterator = Bukkit.getWorlds().iterator();
                while (iterator.hasNext()) {
                    String worldName = iterator.next().getName();
                    TextComponent server = new TextComponent("§a" + worldName);
                    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Rejoindre")));
                    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/world tp " + worldName));
                    message.addExtra(server);
                    if (iterator.hasNext()) message.addExtra(new TextComponent("§7, "));
                }
                input.getPlayer().spigot().sendMessage(message);
            } else if (input.getArg(0).equalsIgnoreCase("reload")) {
                DogeCore.getInstance().getLocationManager().importWorlds();
                input.getPlayer().sendMessage("§f[§bMonde§f] §aChargement des mondes.");
            } else if (input.getArg(0).equalsIgnoreCase("tp") && input.size() > 1) {
                World world = Bukkit.getWorld(input.getArg(1));
                if (world == null) {
                    throw new CommandException("Impossible de trouver ce monde.");
                }
                input.getPlayer().teleport(world.getSpawnLocation());
                input.getPlayer().sendMessage("§f[§bMonde§f] §aVous venez de vous teleporter au monde §e" + world.getName() + "§a.");
            } else {
                throw new CommandInvalidUsageException("/world <list:reload:tp> [monde]");
            }
        } else {
            throw new CommandInvalidUsageException("/world <list:reload>");
        }
    }
}
