package net.dogegames.dogecore.display;

import com.google.common.base.Preconditions;
import net.dogegames.dogecore.api.display.DogeBar;
import net.dogegames.dogecore.api.player.DogePlayer;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class DogeCoreBar implements DogeBar {
    private DogePlayer player;
    private BossBar bar;
    private boolean enabled;
    private String message;
    private double percent;
    private BarColor color;

    public DogeCoreBar(DogePlayer player) {
        this.player = player;
        this.color = BarColor.PINK;
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
            if (enabled) {
                bar = Bukkit.createBossBar(message, color, BarStyle.SOLID);
                bar.addPlayer(Bukkit.getPlayer(player.getUniqueId()));
            } else {
                bar.removePlayer(Bukkit.getPlayer(player.getUniqueId()));
                bar.removeAll();
            }
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        if (!enabled) throw new RuntimeException("The bar is not enabled!");
        if ((this.message == null && message != null) || (this.message != null && !this.message.equals(message))) {
            bar.setTitle(message);
        }
        this.message = message;
    }

    @Override
    public double getPercent() {
        return percent;
    }

    @Override
    public void setPercent(double percent) {
        if (!enabled) throw new RuntimeException("The bar is not enabled!");
        this.percent = percent;
        bar.setProgress(percent / 100);
    }

    @Override
    public BarColor getColor() {
        return color;
    }

    @Override
    public void setColor(BarColor color) {
        if (!enabled) throw new RuntimeException("The bar is not enabled!");
        Preconditions.checkNotNull(color);
        this.color = color;
        bar.setColor(color);
    }
}
