package net.dogegames.dogecore.command.defaults;

import net.dogegames.dogecore.api.command.DogeCommand;
import net.dogegames.dogecore.api.command.DogeCommandInput;
import net.dogegames.dogecore.api.command.exception.CommandException;
import net.dogegames.dogecore.api.permission.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

public class KillAllCommand extends DogeCommand {
    public KillAllCommand() {
        super("killall", Rank.ADMIN, "remove-all", "delete-all", "mobkill", "butcher");
    }

    @Override
    public void run(DogeCommandInput input) throws CommandException {
        Bukkit.getWorlds().forEach(world -> world.getEntities().stream()
                .filter(entity -> !(entity instanceof Painting || entity instanceof ArmorStand || entity instanceof Player))
                .forEach(Entity::remove));
        input.getPlayer().sendMessage("§f[§bEntité§f] §aToutes les entités viennent d'être supprimé.");
    }
}
