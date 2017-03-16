package net.dogegames.dogebungee.command.defaults;

import net.dogegames.dogebungee.DogeBungee;
import net.dogegames.dogebungee.command.DogeBungeeCommand;
import net.dogegames.dogebungee.command.DogeBungeeCommandInput;
import net.dogegames.dogebungee.command.exception.CommandException;
import net.dogegames.dogebungee.command.exception.CommandInvalidUsageException;
import net.dogegames.dogebungee.player.DogeBungeePlayer;
import net.dogegames.dogebungee.player.DogeBungeePlayerSanction;
import net.dogegames.dogebungee.player.permission.Rank;
import net.md_5.bungee.api.chat.TextComponent;

public class PardonCommand extends DogeBungeeCommand {
    public PardonCommand() {
        super("pardon", Rank.ADMIN, "unban");
    }

    @Override
    public void run(DogeBungeeCommandInput input) throws CommandException {
        if (input.size() > 1) {
            DogeBungeePlayer targetEntity = DogeBungee.getInstance().getDatastore().find(DogeBungeePlayer.class).field("username").equalIgnoreCase(input.getArg(0)).get();
            if (targetEntity == null) {
                throw new CommandException("Le joueur demandé est introuvable.");
            } else if (targetEntity.getBan() == null) {
                throw new CommandException("Ce joueur n'a aucun ban d'actif.");
            }
            DogeBungeePlayerSanction sanction = targetEntity.getBan();
            sanction.cancel(input.getEntity(), input.getAll(1));
            DogeBungee.getInstance().getDatastore().save(targetEntity);
            input.getPlayer().sendMessage(TextComponent.fromLegacyText("§f[§bPardon§f] §aVous venez de débannir " + targetEntity.getUsername() + "."));
        } else {
            throw new CommandInvalidUsageException("/unban <joueur> <raison>");
        }
    }
}
