package com.mewo.mewosgear.content.modifiers;

import net.minecraft.world.entity.LivingEntity;

public class ModModifiers {
    public static IModifier createModifier(String name, int tier, ModifierCategory category) {
        return new SimpleModifier(name, tier, category);
    }

    public static final IModifier FIRE_MODIFIER = new SimpleModifier("fire_modifier", 1, ModifierCategory.ONHIT) {
        @Override
        public void onHit(LivingEntity target) {
            target.setSecondsOnFire(5);
        }
    };


}
