package com.github.Glatinis.godTrident.listeners;

import com.github.Glatinis.godTrident.CooldownManager;
import com.github.Glatinis.godTrident.GodTridentItem;
import com.github.Glatinis.godTrident.abilities.AirRiptideAbility;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public final class AirRiptideListener implements Listener {

    private final CooldownManager cooldownManager;
    private final AirRiptideAbility ability = new AirRiptideAbility();

    public AirRiptideListener(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
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

        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.startAutoSpinAttack(20, 0.0f, nmsPlayer.getMainHandItem());

        Vector velocity = player.getLocation().getDirection().multiply(3.0);
        player.setVelocity(velocity);
        cooldownManager.setCooldown(player.getUniqueId(), ability.getName(), ability.getCooldownMillis());
    }
}
