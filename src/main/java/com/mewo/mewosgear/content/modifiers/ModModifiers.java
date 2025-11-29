package com.mewo.mewosgear.content.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class ModModifiers {
    public static Map<String, IModifier> modifiers = new HashMap<String, IModifier>();

    public static IModifier createModifier(String name, int tier, ModifierCategory category) {
        IModifier modifier = new SimpleModifier(name, tier, category);
        modifiers.put(name, modifier);
        return modifier;
    }

    public static IModifier getModifier(String name) {
        return modifiers.get(name);
    }

    public static final IModifier FIRE_MODIFIER = new SimpleModifier("fire_modifier", 1, ModifierCategory.ONHIT) {
        @Override
        public void onHit(LivingEntity target) {
            target.setSecondsOnFire(5);
        }
    };

    public static final IModifier POISON_MODIFIER = new SimpleModifier("poison_modifier", 1, ModifierCategory.ONHIT) {
        @Override
        public void onHit(LivingEntity target) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 360));
        }
    };


}
