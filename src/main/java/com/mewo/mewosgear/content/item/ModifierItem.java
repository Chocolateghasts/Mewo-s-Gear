package com.mewo.mewosgear.content.item;

import com.mewo.mewosgear.content.modifiers.IModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModifierItem extends Item {
    private IModifier currentModifier;

    public ModifierItem(Properties properties, IModifier modifier) {
        super(properties);
        this.currentModifier = modifier;
    }

    public IModifier getCurrentModifier() {
        return currentModifier;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
