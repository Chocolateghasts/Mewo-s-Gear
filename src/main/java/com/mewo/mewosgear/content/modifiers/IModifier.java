package com.mewo.mewosgear.content.modifiers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import static com.mewo.mewosgear.Main.MOD_ID;

public interface IModifier {
    String getName();
    int getTier();
    ModifierCategory getCategory();

    void apply();
    void remove();
    void activate();
    void setEffect();
    void onHit(LivingEntity target);

    default Component getDisplayName() {
        return Component.translatable("modifier." + MOD_ID + "." + getName());
    }
}
