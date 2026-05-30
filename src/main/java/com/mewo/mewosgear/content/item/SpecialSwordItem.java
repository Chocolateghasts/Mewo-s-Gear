package com.mewo.mewosgear.content.item;

import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModModifiers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.HashSet;
import java.util.Set;

import static com.mewo.mewosgear.Main.MOD_ID;

public abstract class SpecialSwordItem extends SwordItem {
    private final Set<IModifier> modifiers = new HashSet<>();

    private static final int DEFAULT_MAX_MODIFIER_LEVEL = 5;

    private int maxModifierLevel = DEFAULT_MAX_MODIFIER_LEVEL;

    public SpecialSwordItem(Tier tier, int dmg, float aspd, int maxModifierLevel, Properties properties) {
        super(tier, dmg, aspd, properties);
        this.maxModifierLevel = maxModifierLevel;
    }

    public void init(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        modNbt.putInt("maxModifierLevel", this.maxModifierLevel);
        nbt.put(MOD_ID, modNbt);
    }

    public boolean addToNBT(IModifier modifier, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        ListTag list = modNbt.getList("modifiers", Tag.TAG_STRING);
        boolean success = list.add(StringTag.valueOf(modifier.getName()));
        modNbt.put("modifiers", list);
        nbt.put(MOD_ID, modNbt);
        return success;
    }

    public boolean removeFromNBT(IModifier modifier, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        ListTag list = modNbt.getList("modifiers", Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) {
            String modifierName = list.getString(i);
            if (modifierName.equalsIgnoreCase(modifier.getName())) {
                list.remove(i);
                modNbt.put("modifiers", list);
                nbt.put(MOD_ID, modNbt);
                return true;
            }
        }
        return false;
    }

    public boolean addModifier(IModifier modifier, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        int modifierLevel = modNbt.getInt("modifierLevel");
        int maxModifierLevel = modNbt.getInt("maxModifierLevel");

        if (modifier.getTier() + modifierLevel <= maxModifierLevel) {
            modNbt.putInt("modifierLevel", modifierLevel + modifier.getTier());
            nbt.put(MOD_ID, modNbt);
            return addToNBT(modifier, stack);
        }
        return false;
    }

    public boolean removeModifier(IModifier modifier, ItemStack stack) {
        if (removeFromNBT(modifier, stack)) {
            CompoundTag nbt = stack.getOrCreateTag();
            CompoundTag modNbt = nbt.getCompound(MOD_ID);
            int modifierLevel = modNbt.getInt("modifierLevel");
            modNbt.putInt("modifierLevel", modifierLevel - modifier.getTier());
            nbt.put(MOD_ID, modNbt);
            return true;
        }
        return false;
    }

    public Set<IModifier> getModifiers(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        ListTag list = modNbt.getList("modifiers", Tag.TAG_STRING);

        Set<IModifier> modifierSet = new HashSet<>();

        for (int i = 0; i < list.size(); i++) {
            String name = list.getString(i);
            IModifier modifier = ModModifiers.getModifier(name);
            if (modifier != null) {
                modifierSet.add(ModModifiers.getModifier(name));
            }
        }

        return Set.copyOf(modifierSet);
    }
}

