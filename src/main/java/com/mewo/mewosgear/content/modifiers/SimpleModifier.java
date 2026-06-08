package com.mewo.mewosgear.content.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SimpleModifier implements IModifier {
    private String name;
    private int tier;
    private ModifierCategory category;
    private int color;

    public SimpleModifier(String name, int tier, ModifierCategory category, int color) {
        this.name = name;
        this.tier = tier;
        this.category = category;
        this.color = color;
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
    public ModifierCategory getCategory() {
        return category;
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
