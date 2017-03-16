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
import org.bukkit.Location;

import java.util.Iterator;

public class LocationCommand extends DogeCommand {
    public LocationCommand() {
        super("location", Rank.ADMIN, "loc");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            if (input.getArg(0).equalsIgnoreCase("list") || input.getArg(0).equalsIgnoreCase("ls")) {
                TextComponent message = new TextComponent("§6Voici la liste des positions disponible : ");
                Iterator<String> iterator = DogeCore.getInstance().getLocationManager().getLocations().keySet().iterator();
                while (iterator.hasNext()) {
                    String locationName = iterator.next();
                    TextComponent server = new TextComponent("§a" + locationName);
                    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Rejoindre")));
                    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/location tp " + locationName));
                    message.addExtra(server);
                    if (iterator.hasNext()) message.addExtra(new TextComponent("§7, "));
                }
                input.getPlayer().spigot().sendMessage(message);
            } else if (input.getArg(0).equalsIgnoreCase("set") && input.size() > 1) {
                DogeCore.getInstance().getLocationManager().setLocation(input.getArg(1), input.getPlayer().getLocation());
                input.getPlayer().sendMessage("§f[§bLocation§f] §aVous venez de définir la position §e" + input.getArg(1).toLowerCase() + "§a.");
            } else if (input.getArg(0).equalsIgnoreCase("tp") && input.size() > 1) {
                Location location = DogeCore.getInstance().getLocationManager().getLocation(input.getArg(1));
                if (location == null) {
                    throw new CommandException("Cette position n'existe pas.");
                }
                input.getPlayer().teleport(location);
                input.getPlayer().sendMessage("§f[§bLocation§f] §aVous venez de vous téléporter à la position §e" + input.getArg(1) + "§a.");
            } else {
                throw new CommandInvalidUsageException("/location <list:set:tp> [nom]");
            }
        } else {
            throw new CommandInvalidUsageException("/location <list:set:tp> [nom]");
        }
    }
}
