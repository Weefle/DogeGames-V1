package net.dogegames.dogebungee.command;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.permission.Rank;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public abstract class DogeBungeeCommand extends Command {
    private String command;
    private Rank rank;
    private String[] aliases;

    /**
     * @param command Command's name.
     * @param rank    Rank required to use this command.
     * @param aliases Command's alias.
     */
    public DogeBungeeCommand(String command, Rank rank, String... aliases) {
        super(command, null, aliases);
        this.command = command;
        this.rank = rank;
        this.aliases = aliases;
    }

    @Override
    public final void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cSeul un joueur peut effectuer cette commande !"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        DogeBungeePlayer entity = DogeBungee.getInstance().getPlayerManager().getPlayer(player);
        if (entity.getRank().getPower() >= rank.getPower()) {
            try {
                run(new DogeBungeeCommandInput(player, entity, command, args));
            } catch (CommandInvalidUsageException e) {
                player.sendMessage(TextComponent.fromLegacyText("§cUsage : " + e.getMessage()));
            } catch (CommandException e) {
                player.sendMessage(TextComponent.fromLegacyText("§cErreur : " + e.getMessage()));
            } catch (Throwable e) {
                player.sendMessage(TextComponent.fromLegacyText("§cLe serveur à rencontré une erreur lors de l'éxecution de cette commande."));
                DogeBungee.getInstance().getLogger().severe("Error while executing command: " + e.getMessage());
            }
        } else {
            player.sendMessage(TextComponent.fromLegacyText("§cErreur : Vous n'avez pas la permission d'éxecuter cette commande."));
        }
    }

    /**
     * @return Command's name.
     */
    public final String getCommand() {
        return command;
    }

    /**
     * @return Rank required to use this command.
     */
    public final Rank getRank() {
        return rank;
    }

    /**
     * @return Command's alias.
     */
    public final String[] getAliases() {
        return aliases;
    }

    /**
     * Run the command.
     *
     * @param input The command input.
     * @throws CommandException If there is a command exception like player not found...
     */
    public abstract void run(DogeBungeeCommandInput input) throws CommandException;
}
