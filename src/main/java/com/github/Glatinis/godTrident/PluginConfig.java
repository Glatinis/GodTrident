package com.github.Glatinis.godTrident;

public final class PluginConfig {

    private final double smashDamage;
    private final double baseDamage;

    public PluginConfig(GodTrident plugin) {
        this.smashDamage = plugin.getConfig().getDouble("smash-damage", 20.0);
        this.baseDamage  = plugin.getConfig().getDouble("trident-base-damage", 10.0);
    }

    public double getSmashDamage() { return smashDamage; }
    public double getBaseDamage()  { return baseDamage;  }
}
