package net.dogegames.dogecore.nms;

import com.comphenix.packetwrapper.WrapperPlayServerChat;
import com.comphenix.packetwrapper.WrapperPlayServerPlayerListHeaderFooter;
import com.comphenix.packetwrapper.WrapperPlayServerTitle;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.dogegames.dogecore.api.helper.ReflectionHelper;
import net.dogegames.dogecore.api.nms.ProtocolAccess;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Map;

public class ProtocolAccess1_9 implements ProtocolAccess {
    @Override
    public void sendTablist(Player player, String header, String footer) {
        WrapperPlayServerPlayerListHeaderFooter packet = new WrapperPlayServerPlayerListHeaderFooter();
        packet.setHeader(WrappedChatComponent.fromText(header));
        packet.setFooter(WrappedChatComponent.fromText(footer));
        packet.sendPacket(player);
    }

    @Override
    public void sendActionbarMessage(Player player, String message) {
        WrapperPlayServerChat packet = new WrapperPlayServerChat();
        packet.setMessage(WrappedChatComponent.fromText(message));
        packet.setPosition((byte) 2);
        packet.sendPacket(player);
    }

    @Override
    public void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
        if (title != null) {
            WrapperPlayServerTitle titlePacket = new WrapperPlayServerTitle();
            titlePacket.setAction(EnumWrappers.TitleAction.TITLE);
            titlePacket.setFadeIn(fadeIn);
            titlePacket.setStay(stay);
            titlePacket.setFadeOut(fadeOut);
            titlePacket.sendPacket(player);
        }
        if (subtitle != null) {
            WrapperPlayServerTitle subtitlePacket = new WrapperPlayServerTitle();
            subtitlePacket.setAction(EnumWrappers.TitleAction.SUBTITLE);
            subtitlePacket.setFadeIn(fadeIn);
            subtitlePacket.setStay(stay);
            subtitlePacket.setFadeOut(fadeOut);
            subtitlePacket.sendPacket(player);
        }
    }

    @Override
    public void registerCommand(Command command) {
        ((CraftServer) Bukkit.getServer()).getCommandMap().register("\u0001", command);
    }

    @Override
    public void unregisterCommand(String name) {
        try {
            SimpleCommandMap commandMap = ((CraftServer) Bukkit.getServer()).getCommandMap();
            Map<String, Command> commands = (Map<String, Command>) ReflectionHelper.getValue(commandMap, true, "knownCommands");
            if (commands.containsKey(name)) {
                Command command = commands.get(name);
                command.getAliases().forEach(commands::remove);
                commands.remove(command.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCommandMap(SimpleCommandMap map) {
        try {
            ReflectionHelper.setValue((CraftServer) Bukkit.getServer(), true, "commandMap", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double[] getTps() {
        return ((CraftServer) Bukkit.getServer()).getServer().recentTps;
    }

    @Override
    public int getPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }
}
