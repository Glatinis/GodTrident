package com.github.Glatinis.godTrident;

import com.github.Glatinis.godTrident.commands.GiveGodTridentCommand;
import com.github.Glatinis.godTrident.listeners.AirRiptideListener;
import com.github.Glatinis.godTrident.listeners.CritLightningListener;
import com.github.Glatinis.godTrident.listeners.FallDamageListener;
import com.github.Glatinis.godTrident.listeners.SmashListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class GodTrident extends JavaPlugin {

    private static GodTrident instance;
    private CooldownManager cooldownManager;

    public static GodTrident getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        PluginConfig config = new PluginConfig(this);
        GodTridentItem.init(this, config);
        cooldownManager = new CooldownManager();
        registerRecipes();
        registerListeners(config);
        registerCommands();
        getLogger().info("GodTrident enabled.");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("GodTrident disabled.");
    }

    private void registerRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(
            new NamespacedKey(this, "god_trident"), GodTridentItem.create());
        recipe.shape("DDD", "RTR", "RHR");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);
        recipe.setIngredient('T', Material.TRIDENT);
        recipe.setIngredient('H', Material.HEART_OF_THE_SEA);
        getServer().addRecipe(recipe);
    }

    private void registerListeners(PluginConfig config) {
        Set<UUID> spinAttacking = new HashSet<>();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new AirRiptideListener(cooldownManager, this, spinAttacking, config.getSmashDamage()), this);
        pm.registerEvents(new SmashListener(spinAttacking), this);
        pm.registerEvents(new CritLightningListener(cooldownManager), this);
        pm.registerEvents(new FallDamageListener(), this);
    }

    private void registerCommands() {
        PluginCommand cmd = Objects.requireNonNull(getCommand("givegodtrident"));
        GiveGodTridentCommand handler = new GiveGodTridentCommand();
        cmd.setExecutor(handler);
        cmd.setTabCompleter(handler);
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
