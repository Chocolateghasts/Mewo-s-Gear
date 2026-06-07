package com.mewo.mewosgear.content.registry;

import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModifierCategory;
import com.mewo.mewosgear.content.modifiers.SimpleModifier;
import net.minecraft.world.effect.MobEffectInstance;
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
            target.addEffect(new MobEffectInstance(ModEffects.TRUE_POISON.get(), 360));
        }
    };

    static {
        modifiers.put(FIRE_MODIFIER.getName(), FIRE_MODIFIER);
        modifiers.put(POISON_MODIFIER.getName(), POISON_MODIFIER);
    }
}
