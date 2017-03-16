package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.player.permission.Rank;
import net.dogegames.dogeprotocol.packet.proxy.ProxyBroadcastPacket;
import net.md_5.bungee.api.ChatColor;

public class BroadcastCommand extends DogeBungeeCommand {
    public BroadcastCommand() {
        super("gbroadcast", Rank.ADMIN, "gbc", "gcast");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            DogeBungee.getInstance().getProtocolManager().writePacket(new ProxyBroadcastPacket(ChatColor.translateAlternateColorCodes('&', input.getAll())));
        } else {
            throw new CommandInvalidUsageException("/gbroadcast <message>");
        }
    }
}
