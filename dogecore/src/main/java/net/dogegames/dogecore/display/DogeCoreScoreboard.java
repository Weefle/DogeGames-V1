package net.dogegames.dogecore.display;

import com.comphenix.packetwrapper.WrapperPlayServerScoreboardDisplayObjective;
import com.comphenix.packetwrapper.WrapperPlayServerScoreboardObjective;
import com.comphenix.packetwrapper.WrapperPlayServerScoreboardScore;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Preconditions;
import net.dogegames.dogecore.api.display.DogeScoreboard;
import net.dogegames.dogecore.api.helper.TextHelper;
import net.dogegames.dogecore.player.DogeCorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DogeCoreScoreboard implements DogeScoreboard {
    private DogeCorePlayer player;
    private boolean enabled;
    private String title;
    private String[] lines;

    public DogeCoreScoreboard(DogeCorePlayer player) {
        this.player = player;
        this.title = ChatColor.GOLD + TextHelper.centerText("DogeGames", 19) + "§r";
        this.lines = new String[16];
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        boolean update = this.enabled != enabled;
        this.enabled = enabled;
        if (update) {
            if (!enabled) {
                deleteObjective();
            } else {
                createObjective();
                createDisplay();
            }
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        if (!enabled) throw new RuntimeException("The scoreboard is not enabled!");
        Preconditions.checkNotNull(title);
        this.title = title;
    }

    @Override
    public String[] getLines() {
        return lines;
    }

    @Override
    public void setLine(int line, String value) {
        if (!enabled) throw new RuntimeException("The scoreboard is not enabled!");
        if (value == null) {
            updateLine(line, null, EnumWrappers.ScoreboardAction.REMOVE);
        } else if (!value.equals(lines[line])) {
            updateLine(line, value, EnumWrappers.ScoreboardAction.CHANGE);
        }
        lines[line] = value;
    }

    private Player getPlayer() {
        return Bukkit.getPlayer(player.getUniqueId());
    }

    private void createObjective() {
        WrapperPlayServerScoreboardObjective packet = new WrapperPlayServerScoreboardObjective();
        packet.setName(getPlayer().getName().toLowerCase());
        packet.setDisplayName(title);
        packet.setMode(0);
        packet.setHealthDisplay("integer");
        packet.sendPacket(getPlayer());
    }

    private void deleteObjective() {
        WrapperPlayServerScoreboardObjective packet = new WrapperPlayServerScoreboardObjective();
        packet.setName(getPlayer().getName().toLowerCase());
        packet.setMode(1);
        packet.sendPacket(getPlayer());
    }

    private void createDisplay() {
        WrapperPlayServerScoreboardDisplayObjective packet = new WrapperPlayServerScoreboardDisplayObjective();
        packet.setScoreName(getPlayer().getName().toLowerCase());
        packet.setPosition(1);
        packet.sendPacket(getPlayer());
    }

    private void updateTitle() {
        WrapperPlayServerScoreboardObjective packet = new WrapperPlayServerScoreboardObjective();
        packet.setName(getPlayer().getName().toLowerCase());
        packet.setDisplayName(this.title);
        packet.setMode(2);
        packet.sendPacket(getPlayer());
    }

    private void updateLine(int line, String value, EnumWrappers.ScoreboardAction action) {
        WrapperPlayServerScoreboardScore packet = new WrapperPlayServerScoreboardScore();
        packet.setObjectiveName(getPlayer().getName().toLowerCase());
        packet.setScoreboardAction(action);
        packet.setScoreName(value);
        packet.setValue(line);
        packet.sendPacket(getPlayer());
    }
}
