package com.mewo.mewosgear.content.Item;

import com.mewo.mewosgear.content.modifiers.IModifier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.HashSet;
import java.util.Set;

public abstract class SpecialSwordItem extends SwordItem implements IWeapon{
    private final Set<IModifier> modifiers = new HashSet<>();
    private int maxModifierLevel;
    private int modifierLevel;

    public SpecialSwordItem(Tier tier, int dmg, float aspd, Properties properties) {
        super(tier, dmg, aspd, properties);
    }

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

    public Set<IModifier> getModifiers() {
        return Set.copyOf(modifiers);
    }
}
