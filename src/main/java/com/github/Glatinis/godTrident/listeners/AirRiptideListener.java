package com.github.Glatinis.godTrident.listeners;

import com.github.Glatinis.godTrident.CooldownManager;
import com.github.Glatinis.godTrident.GodTrident;
import com.github.Glatinis.godTrident.GodTridentItem;
import com.github.Glatinis.godTrident.abilities.AirRiptideAbility;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class AirRiptideListener implements Listener {

    private static final int MIN_CHARGE_TICKS = 5;
    private static final int MAX_CHARGE_TICKS = 20;

    private final CooldownManager cooldownManager;
    private final AirRiptideAbility ability = new AirRiptideAbility();
    private final GodTrident plugin;
    private final Set<UUID> spinAttacking;
    private final Set<UUID> chargingPlayers = new HashSet<>();
    private final float smashDamage;

    public AirRiptideListener(CooldownManager cooldownManager, GodTrident plugin, Set<UUID> spinAttacking, double smashDamage) {
        this.cooldownManager = cooldownManager;
        this.plugin = plugin;
        this.spinAttacking = spinAttacking;
        this.smashDamage = (float) smashDamage;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!GodTridentItem.isGodTrident(event.getItem())) return;

        Player player = event.getPlayer();
        if (player.isInWater() || player.isInRain()) return;
        if (cooldownManager.isOnCooldown(player.getUniqueId(), ability.getName())) return;
        if (chargingPlayers.contains(player.getUniqueId())) return;

        event.setCancelled(true);

        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.startUsingItem(InteractionHand.MAIN_HAND);
        chargingPlayers.add(player.getUniqueId());

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (!player.isOnline() || !GodTridentItem.isGodTrident(player.getInventory().getItemInMainHand())) {
                    chargingPlayers.remove(player.getUniqueId());
                    if (nmsPlayer.isUsingItem()) nmsPlayer.stopUsingItem();
                    cancel();
                    return;
                }

                ticks++;
                boolean stillCharging = nmsPlayer.isUsingItem();

                if (!stillCharging || ticks >= MAX_CHARGE_TICKS) {
                    chargingPlayers.remove(player.getUniqueId());
                    if (ticks >= MIN_CHARGE_TICKS) {
                        if (stillCharging) nmsPlayer.stopUsingItem();
                        launchPlayer(player, nmsPlayer);
                    } else {
                        if (nmsPlayer.isUsingItem()) nmsPlayer.stopUsingItem();
                    }
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

    private void launchPlayer(Player player, ServerPlayer nmsPlayer) {
        nmsPlayer.startAutoSpinAttack(20, smashDamage, nmsPlayer.getMainHandItem());
        spinAttacking.add(player.getUniqueId());
        new BukkitRunnable() {
            @Override public void run() { spinAttacking.remove(player.getUniqueId()); }
        }.runTaskLater(plugin, 20L);

        Vector velocity = player.getLocation().getDirection().multiply(3.0);
        player.setVelocity(velocity);
        cooldownManager.setCooldown(player.getUniqueId(), ability.getName(), ability.getCooldownMillis());
    }
}
