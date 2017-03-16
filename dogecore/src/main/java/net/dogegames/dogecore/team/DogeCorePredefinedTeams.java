package net.dogegames.dogecore.team;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.team.DogePredefinedTeams;
import net.dogegames.dogecore.api.team.DogeTeam;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public class DogeCorePredefinedTeams implements DogePredefinedTeams {
    private DogeCore plugin;
    private DogeTeam redTeam;
    private DogeTeam blueTeam;
    private DogeTeam greenTeam;
    private DogeTeam yellowTeam;
    private DogeTeam whiteTeam;
    private DogeTeam purpleTeam;
    private DogeTeam orangeTeam;
    private DogeTeam pinkTeam;

    public DogeCorePredefinedTeams(DogeCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public DogeTeam getRedTeam() {
        if (redTeam == null) {
            redTeam = plugin.getTeamManager().createTeam("Rouge", ChatColor.RED, Color.RED, DyeColor.RED);
        }
        return redTeam;
    }

    @Override
    public DogeTeam getBlueTeam() {
        if (blueTeam == null) {
            blueTeam = plugin.getTeamManager().createTeam("Bleue", ChatColor.BLUE, Color.BLUE, DyeColor.BLUE);
        }
        return blueTeam;
    }

    @Override
    public DogeTeam getGreenTeam() {
        if (greenTeam == null) {
            greenTeam = plugin.getTeamManager().createTeam("Verte", ChatColor.GREEN, Color.GREEN, DyeColor.GREEN);
        }
        return greenTeam;
    }

    @Override
    public DogeTeam getYellowTeam() {
        if (yellowTeam == null) {
            yellowTeam = plugin.getTeamManager().createTeam("Jaune", ChatColor.YELLOW, Color.YELLOW, DyeColor.YELLOW);
        }
        return yellowTeam;
    }

    @Override
    public DogeTeam getWhiteTeam() {
        if (whiteTeam == null) {
            whiteTeam = plugin.getTeamManager().createTeam("Blanche", ChatColor.WHITE, Color.WHITE, DyeColor.WHITE);
        }
        return whiteTeam;
    }

    @Override
    public DogeTeam getPurpleTeam() {
        if (purpleTeam == null) {
            purpleTeam = plugin.getTeamManager().createTeam("Violette", ChatColor.DARK_PURPLE, Color.PURPLE, DyeColor.PURPLE);
        }
        return purpleTeam;
    }

    @Override
    public DogeTeam getOrangeTeam() {
        if (orangeTeam == null) {
            orangeTeam = plugin.getTeamManager().createTeam("Orange", ChatColor.YELLOW, Color.ORANGE, DyeColor.ORANGE);
        }
        return orangeTeam;
    }

    @Override
    public DogeTeam getPinkTeam() {
        if (pinkTeam == null) {
            pinkTeam = plugin.getTeamManager().createTeam("Rose", ChatColor.LIGHT_PURPLE, Color.FUCHSIA, DyeColor.PINK);
        }
        return pinkTeam;
    }
}
