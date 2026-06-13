package com.github.Glatinis.godTrident;

import com.github.Glatinis.godTrident.commands.GiveGodTridentCommand;
import com.github.Glatinis.godTrident.listeners.AirRiptideListener;
import com.github.Glatinis.godTrident.listeners.CritLightningListener;
import com.github.Glatinis.godTrident.listeners.FallDamageListener;
import com.github.Glatinis.godTrident.listeners.SmashListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class GodTrident extends JavaPlugin {

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        GodTridentItem.init(this);
        cooldownManager = new CooldownManager();

        double smashDamage = getConfig().getDouble("smash-damage", 20.0);
        Set<UUID> spinAttacking = new HashSet<>();

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "god_trident"), GodTridentItem.create());
        recipe.shape("DDD", "RTR", "RHR");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);
        recipe.setIngredient('T', Material.TRIDENT);
        recipe.setIngredient('H', Material.HEART_OF_THE_SEA);
        getServer().addRecipe(recipe);

        getCommand("givegodtrident").setExecutor(new GiveGodTridentCommand());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new AirRiptideListener(cooldownManager, this, spinAttacking, smashDamage), this);
        pm.registerEvents(new SmashListener(spinAttacking), this);
        pm.registerEvents(new CritLightningListener(cooldownManager), this);
        pm.registerEvents(new FallDamageListener(), this);
        // Register ability listeners here as they are added:
        // pm.registerEvents(new YourAbilityListener(cooldownManager), this);
    }

    @Override
    public void onDisable() {
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
