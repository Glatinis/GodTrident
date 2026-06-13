package com.github.Glatinis.godTrident.listeners;

import com.github.Glatinis.godTrident.GodTridentItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class FallDamageListener implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (!GodTridentItem.isGodTrident(player.getInventory().getItemInMainHand())) return;

        event.setCancelled(true);
    }
}
