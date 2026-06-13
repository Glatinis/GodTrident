package com.github.Glatinis.godTrident.listeners;

import com.github.Glatinis.godTrident.CooldownManager;
import com.github.Glatinis.godTrident.GodTridentItem;
import com.github.Glatinis.godTrident.abilities.CritLightningAbility;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public final class CritLightningListener implements Listener {

    private final CooldownManager cooldownManager;
    private final CritLightningAbility ability = new CritLightningAbility();

    public CritLightningListener(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onCrit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!GodTridentItem.isGodTrident(player.getInventory().getItemInMainHand())) return;
        if (!(event.getEntity() instanceof LivingEntity victim)) return;
        if (cooldownManager.isOnCooldown(player.getUniqueId(), ability.getName())) return;

        // Crit requires the player to be airborne and falling downward
        if (player.isOnGround() || player.getVelocity().getY() >= 0) return;

        victim.getWorld().strikeLightning(victim.getLocation());
        cooldownManager.setCooldown(player.getUniqueId(), ability.getName(), ability.getCooldownMillis());
    }
}
