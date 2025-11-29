package com.mewo.mewosgear.content.Item;

import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModModifiers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.HashSet;
import java.util.Set;

import static com.mewo.mewosgear.Main.MOD_ID;
import static com.mewo.mewosgear.content.modifiers.ModifierCategory.ONHIT;

public abstract class SpecialSwordItem extends SwordItem implements IWeapon {
    private final Set<IModifier> modifiers = new HashSet<>();
    private int maxModifierLevel;
    private int modifierLevel;
    private int testInt = 0;

    private static final int MAX_MODIFIER_LEVEL = 5;

    public SpecialSwordItem(Tier tier, int dmg, float aspd, Properties properties) {
        super(tier, dmg, aspd, properties);
    }


    // TODO: Make it per-itemstack, not global
    public boolean addModifier(IModifier modifier) {
        if (modifierLevel + modifier.getTier() <= maxModifierLevel) {
            if (modifiers.add(modifier)) {
                modifierLevel += modifier.getTier();
                return true;
            }
        }
        return false;
    }

    public boolean removeModifier(IModifier modifier) {
        if (modifiers.remove(modifier)) {
            modifierLevel -= modifier.getTier();
            return true;
        }
        return false;
    }

    public Set<IModifier> getModifiers(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);

        return Set.copyOf(modifiers);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity ihavenoidea) {
        if (super.hurtEnemy(stack, ihavenoidea, target)) {
            testInt++;
            if (testInt % 8 == 0) {
                addModifier(ModModifiers.POISON_MODIFIER);
            }
            for (IModifier modifier : getModifiers(stack)) {
                if (modifier.getCategory() == ONHIT) {
                    modifier.onHit(target);
                }
            }
            return true;
        }
        return false;
    }

    public int getMaxModifierLevel(ItemStack stack) {
        CompoundTag nbt =  stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        int maxLevel = modNbt.getInt("MaxModifierLevel");
        if (maxLevel > 0) return maxLevel;
        return MAX_MODIFIER_LEVEL;
    }

    public void setMaxModifierLevel(int maxModifierLevel) {
        this.maxModifierLevel = maxModifierLevel;
    }

    public void setModifierLevel(int modifierLevel) {
        this.modifierLevel = modifierLevel;
    }
}
