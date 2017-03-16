package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.player.permission.Rank;
import net.dogegames.dogeprotocol.packet.proxy.ProxyStaffChatPacket;
import net.md_5.bungee.api.chat.TextComponent;

public class StaffCommand extends DogeBungeeCommand {
    public StaffCommand() {
        super("staff", Rank.HELPER, "staffchat", "stc");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 0) {
            DogeBungee.getInstance().getProtocolManager().writePacket(new ProxyStaffChatPacket(
                    input.getEntity().getUsername(),
                    (byte) input.getEntity().getRank().ordinal(),
                    input.getAll()));
        } else {
            input.getEntity().setUsingStaffChat(!input.getEntity().isUsingStaffChat());
            input.getPlayer().sendMessage(TextComponent.fromLegacyText("§f[§bStaff§f] §aStaff chat : " + (input.getEntity().isUsingStaffChat() ? "activé" : "désativé") + "."));
        }
    }
}
