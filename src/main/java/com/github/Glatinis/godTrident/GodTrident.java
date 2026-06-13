package com.github.Glatinis.godTrident;

import com.github.Glatinis.godTrident.commands.GiveGodTridentCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class GodTrident extends JavaPlugin {

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        GodTridentItem.init(this);
        cooldownManager = new CooldownManager();
        getCommand("givegodtrident").setExecutor(new GiveGodTridentCommand());
        // Register ability listeners here as they are added:
        // getServer().getPluginManager().registerEvents(new YourAbilityListener(cooldownManager), this);
    }

    @Override
    public void onDisable() {
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}

