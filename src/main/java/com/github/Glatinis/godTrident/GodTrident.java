package com.github.Glatinis.godTrident;

import com.github.Glatinis.godTrident.commands.GiveGodTridentCommand;
import com.github.Glatinis.godTrident.listeners.AirRiptideListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class GodTrident extends JavaPlugin {

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        GodTridentItem.init(this);
        cooldownManager = new CooldownManager();
        getCommand("givegodtrident").setExecutor(new GiveGodTridentCommand());
        getServer().getPluginManager().registerEvents(new AirRiptideListener(cooldownManager), this);
    }

    @Override
    public void onDisable() {
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}

