package com.github.Glatinis.godTrident;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CooldownManager {

    private final Map<String, Map<UUID, Long>> cooldowns = new HashMap<>();

    public boolean isOnCooldown(UUID playerId, String abilityName) {
        Map<UUID, Long> map = cooldowns.get(abilityName);
        if (map == null) return false;
        Long expiry = map.get(playerId);
        if (expiry == null) return false;
        if (System.currentTimeMillis() >= expiry) {
            map.remove(playerId);
            return false;
        }
        return true;
    }

    public void setCooldown(UUID playerId, String abilityName, long durationMillis) {
        cooldowns.computeIfAbsent(abilityName, k -> new HashMap<>())
                 .put(playerId, System.currentTimeMillis() + durationMillis);
    }

    public long getRemainingMillis(UUID playerId, String abilityName) {
        Map<UUID, Long> map = cooldowns.get(abilityName);
        if (map == null) return 0;
        Long expiry = map.get(playerId);
        if (expiry == null) return 0;
        return Math.max(0, expiry - System.currentTimeMillis());
    }
}
