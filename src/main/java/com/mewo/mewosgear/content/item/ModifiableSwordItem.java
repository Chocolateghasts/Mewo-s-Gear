package com.mewo.mewosgear.content.item;

import com.mewo.mewosgear.content.modifiers.IModifiable;
import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModifierEnums;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierCategory.*;
import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierToolType.*;

public class ModifiableSwordItem extends SwordItem implements IModifiable {
    private int maxModifierLevel;
    public ModifiableSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, int maxModifierLevel, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.maxModifierLevel = maxModifierLevel;
    }



    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        for (IModifier modifier : getModifiers(stack)) {
            if (modifier.getCategory() == ON_HIT) {
                modifier.onHit(stack, target, attacker);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        appendModifierText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public int getMaxModifierLevel() {
        return maxModifierLevel;
    }

    @Override
    public boolean allowsModifier(IModifier modifier) {
        ModifierEnums.ModifierToolType toolType = modifier.getToolType();
        return toolType == SWORD || toolType == WEAPON;
    }
}
