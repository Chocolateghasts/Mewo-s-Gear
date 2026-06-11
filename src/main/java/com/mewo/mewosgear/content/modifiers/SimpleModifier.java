package com.mewo.mewosgear.content.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SimpleModifier implements IModifier {
    private String name;
    private int tier;
    private ModifierEnums.ModifierCategory category;
    private ModifierEnums.ModifierToolType toolType;
    private int color;

    public SimpleModifier(String name, int tier, ModifierEnums.ModifierCategory category, ModifierEnums.ModifierToolType toolType, int color) {
        this.name = name;
        this.tier = tier;
        this.category = category;
        this.color = color;
        this.toolType = toolType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public ModifierEnums.ModifierCategory getCategory() {
        return category;
    }

    public ModifierEnums.ModifierToolType getToolType() {
        return toolType;
    }

    @Override
    public void apply() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void setEffect() {

    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    }

    @Override
    public int getColor() {
        return color;
    }
}
