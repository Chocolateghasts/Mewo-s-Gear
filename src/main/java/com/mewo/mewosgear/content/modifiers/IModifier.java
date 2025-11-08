package com.mewo.mewosgear.content.modifiers;

import net.minecraft.world.entity.LivingEntity;

public interface IModifier {
    String getName();
    int getTier();
    ModifierCategory getCategory();

    void apply();
    void remove();
    void activate();
    void setEffect();
    void onHit(LivingEntity target);
}
