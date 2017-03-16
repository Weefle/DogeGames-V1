package net.dogegames.dogecore.gui;

import net.dogegames.dogecore.api.gui.DogeGui;
import net.dogegames.dogecore.api.gui.DogeGuiManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DogeCoreGuiManager implements DogeGuiManager {
    private Collection<DogeGui> guis;

    public DogeCoreGuiManager() {
        this.guis = new ArrayList<>();
    }

    @Override
    public void registerGui(DogeGui gui) {
        if (getGui(gui.getClass()) != null) throw new RuntimeException("This gui is already registered!");
        guis.add(gui);
    }

    @Override
    public void open(Class<? extends DogeGui> guiClass, Player player) {
        DogeGui gui = getGui(guiClass);
        if (gui == null) throw new RuntimeException("This gui is not register!");
        Inventory inventory = Bukkit.createInventory(null, gui.getSize(), gui.titleFor(player));
        gui.contentFor(player, inventory);
        player.openInventory(inventory);
    }

    @Override
    public DogeGui getGui(Class<? extends DogeGui> guiClass) {
        for (DogeGui gui : guis) if (gui.getClass() == guiClass) return gui;
        return null;
    }

    @Override
    public Collection<DogeGui> getGuis() {
        return Collections.unmodifiableCollection(guis);
    }
}
