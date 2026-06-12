package com.mewo.mewosgear.content.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.mewo.mewosgear.Main.MOD_ID;

public interface IModifier {
    String getName();
    int getTier();
    ModifierEnums.ModifierCategory getCategory();
    ModifierEnums.ModifierToolType getToolType();

    void apply();
    void remove();
    void activate();
    void setEffect();
    default void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {}
    default void onBreak(ItemStack stack, Level level, BlockState blockState, BlockPos pos, LivingEntity player) {}
    int getColor();

    default Component getDisplayName() {
        return Component.translatable("modifier." + MOD_ID + "." + getName());
    }
}
