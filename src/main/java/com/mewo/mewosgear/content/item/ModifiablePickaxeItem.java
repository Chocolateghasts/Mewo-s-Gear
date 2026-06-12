package com.mewo.mewosgear.content.item;

import com.mewo.mewosgear.content.modifiers.IModifiable;
import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModifierEnums;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierCategory.*;
import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierToolType.*;

public class ModifiablePickaxeItem extends PickaxeItem implements IModifiable {
    private int maxModifierLevel;

    public ModifiablePickaxeItem(Tier tier, int dmg, float aspd, int maxModifierLevel, Properties properties) {
        super(tier, dmg, aspd, properties);
        this.maxModifierLevel = maxModifierLevel;
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
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity player) {
        super.mineBlock(stack, level, state, pos, player);
        if (!level.isClientSide()) {

            for (IModifier modifier : getModifiers(stack)) {
                if (modifier.getCategory() == ON_BREAK) {
                    modifier.onBreak(stack, level, state, pos, player);
                }
            }
        }
        return true;
    }

    @Override
    public boolean allowsModifier(IModifier modifier) {
        ModifierEnums.ModifierToolType toolType = modifier.getToolType();
        return toolType == TOOL || toolType == PICKAXE;
    }

}
