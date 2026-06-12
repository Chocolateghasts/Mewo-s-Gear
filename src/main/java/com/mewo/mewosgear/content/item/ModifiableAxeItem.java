package com.mewo.mewosgear.content.item;

import com.mewo.mewosgear.content.modifiers.IModifiable;
import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModifierEnums;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierCategory.*;
import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierToolType.*;


public class ModifiableAxeItem extends AxeItem implements IModifiable {
    private int maxModifierLevel;

    public ModifiableAxeItem(Tier tier, float dmg, float aspd, int maxModifierLevel, Properties properties) {
        super(tier, dmg, aspd, properties);
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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltipComponents, flag);
        appendModifierText(stack, level, tooltipComponents, flag);
    }

    @Override
    public int getMaxModifierLevel() {
        return maxModifierLevel;
    }

    @Override
    public boolean allowsModifier(IModifier modifier) {
        ModifierEnums.ModifierToolType toolType = modifier.getToolType();
        return toolType == AXE || toolType == WEAPON;
    }
}
