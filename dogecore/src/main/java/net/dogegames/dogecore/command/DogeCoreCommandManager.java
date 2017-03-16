package net.dogegames.dogecore.command;

import net.dogegames.dogecore.DogeCore;
import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandManager;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.command.exception.CommandInvalidUsageException;
import net.dogegames.dogecore.player.DogeCorePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DogeCoreCommandManager implements DogeCommandManager {
    private DogeCore plugin;

    public DogeCoreCommandManager(DogeCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerCommand(DogeCommand command) {
        String help = "/" + command + " help";
        plugin.getProtocolAccess().registerCommand(new Command(command.getCommand(), help, help, Arrays.asList(command.getAliases())) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cSeul un joueur peut effectuer cette commande !");
                    return true;
                }
                Player player = (Player) sender;
                DogeCorePlayer entity = plugin.getPlayerManager().getPlayer(player);
                if (entity.getRank().getPower() >= command.getRank().getPower()) {
                    try {
                        command.run(new DogeCoreCommandInput(player, entity, label, args));
                    } catch (CommandInvalidUsageException e) {
                        player.sendMessage("§cUsage : " + e.getMessage());
                    } catch (CommandException e) {
                        player.sendMessage("§cErreur : " + e.getMessage());
                    } catch (Throwable e) {
                        player.sendMessage("§cLe serveur à rencontré une erreur lors de l'éxecution de cette commande.");
                        plugin.getLogger().severe("Error while executing command: " + e.getMessage());
                    }
                } else {
                    player.sendMessage("§cErreur : Vous n'avez pas la permission d'éxecuter cette commande.");
                }
                return true;
            }
        });
    }

    @Override
    public void unregisterCommand(String command) {
        plugin.getProtocolAccess().unregisterCommand(command);
    }
}
