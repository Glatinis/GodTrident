package com.github.Glatinis.godTrident.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;
import java.util.UUID;

public final class SmashListener implements Listener {

    private final Set<UUID> spinAttacking;

    public SmashListener(Set<UUID> spinAttacking) {
        this.spinAttacking = spinAttacking;
    }

    @EventHandler
    public void onSmashHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!spinAttacking.contains(player.getUniqueId())) return;
        if (!(event.getEntity() instanceof LivingEntity victim)) return;

        victim.getWorld().strikeLightning(victim.getLocation());
    }
}
