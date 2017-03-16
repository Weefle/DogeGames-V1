package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.helper.DateHelper;
import net.dogegames.dogecore.api.helper.TextHelper;
import net.dogegames.dogecore.api.permission.Rank;
import net.md_5.bungee.api.ChatColor;

import java.util.Date;

public class LagCommand extends DogeCommand {
    public LagCommand() {
        super("lag", Rank.PLAYER, "lagg", "ping", "latency", "tps");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        input.getPlayer().sendMessage(ChatColor.GOLD + TextHelper.separator());
        input.getPlayer().sendMessage("§aServeur : §bNOM DU SERVEUR §aDate : §b" + DateHelper.UNTIL_FORMAT.format(new Date()));
        input.getPlayer().sendMessage("§aLatence avec le serveur : §b" + DogeCore.getInstance().getProtocolAccess().getPing(input.getPlayer()) + "ms");
        double[] tps = DogeCore.getInstance().getProtocolAccess().getTps();
        input.getPlayer().sendMessage("§aTPS du serveur : §b" + format(tps[0]) + "§b, " + format(tps[1]) + "§b, " + format(tps[2]));
        input.getPlayer().sendMessage("§7§oLes TPS (ticks per second) indiquent l'état du serveur.");
        input.getPlayer().sendMessage(ChatColor.GOLD + TextHelper.separator());
    }

    private String format(double tps) {
        return (tps > 18.0D ? org.bukkit.ChatColor.GREEN : (tps > 16.0D ? org.bukkit.ChatColor.YELLOW : org.bukkit.ChatColor.RED)).toString() + (tps > 20.0D ? "*" : "") + Math.min((double) Math.round(tps * 100.0D) / 100.0D, 20.0D);
    }
}
