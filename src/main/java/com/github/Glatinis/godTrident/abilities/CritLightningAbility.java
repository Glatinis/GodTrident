package com.github.Glatinis.godTrident.abilities;

public final class CritLightningAbility implements Ability {
    @Override public String getName() { return "crit_lightning"; }
    @Override public long getCooldownMillis() { return 10L; }
}
