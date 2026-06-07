package com.mewo.mewosgear.content.item;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.registry.ModModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mewo.mewosgear.Main.MOD_ID;
import static com.mewo.mewosgear.content.modifiers.ModifierCategory.*;

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

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltipComponents, flag);

        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        int current = modNbt.getInt("modifierLevel");
        int max = modNbt.contains("maxModifierLevel") ? modNbt.getInt("maxModifierLevel") : this.maxModifierLevel;

        tooltipComponents.add(Component.literal("Modifiers: " + current + "/" + max)
                .withStyle(ChatFormatting.GRAY));

        Set<IModifier> modifiers = getModifiers(stack);
        if (modifiers.isEmpty()) {
            tooltipComponents.add(Component.literal("  None").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            for (IModifier modifier : modifiers) {
                tooltipComponents.add(modifier.getDisplayName().copy()
                        .append(Component.literal(" (T" + modifier.getTier() + ")"))
                        .withStyle(ChatFormatting.GREEN));

            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        for (IModifier modifier : getModifiers(stack)) {
            if (modifier.getCategory() == ONHIT) {
                modifier.onHit(target);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
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

    public boolean hasModifier(ItemStack stack, IModifier modifier) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        ListTag modifierList = modNbt.getList("modifiers", Tag.TAG_STRING);
        return modifierList.contains(StringTag.valueOf(modifier.getName()));
    }

    public boolean addModifier(IModifier modifier, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CompoundTag modNbt = nbt.getCompound(MOD_ID);
        if (hasModifier(stack, modifier)) return false;
        int modifierLevel = modNbt.getInt("modifierLevel");
        int maxModifierLevel = modNbt.contains("maxModifierLevel")
                ? modNbt.getInt("maxModifierLevel")
                : this.maxModifierLevel;

        if (!modNbt.contains("maxModifierLevel")) {
            modNbt.putInt("maxModifierLevel", this.maxModifierLevel);
            nbt.put(MOD_ID, modNbt);
        }

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

