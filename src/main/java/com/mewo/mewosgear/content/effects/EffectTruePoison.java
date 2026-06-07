package com.mewo.mewosgear.content.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectTruePoison extends MobEffect {
    public EffectTruePoison() {
        super(MobEffectCategory.HARMFUL, 0x00FF00);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getHealth() > 1.0F) {
            livingEntity.hurt(livingEntity.damageSources().magic(), 1.0F * (amplifier + 1));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 25 >> amplifier;
        return k == 0 || duration % k == 0;
    }
}
