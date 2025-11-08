package com.mewo.mewosgear.content.modifiers;

import net.minecraft.world.entity.LivingEntity;

public class SimpleModifier implements IModifier {
    private String name;
    private int tier;
    private ModifierCategory category;

    public SimpleModifier(String name, int tier, ModifierCategory category) {
        this.name = name;
        this.tier = tier;
        this.category = category;
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
    public void onHit(LivingEntity target) {

    }
}
