package com.mewo.mewosgear.content.registry;


import com.mewo.mewosgear.content.item.ModifiableAxeItem;
import com.mewo.mewosgear.content.item.ModifiableSwordItem;
import com.mewo.mewosgear.content.item.SpecialSwordItem;
import com.mewo.mewosgear.content.modifiers.IModifiable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mewo.mewosgear.Main.MOD_ID;

public class ModWeapons {
    public static final DeferredRegister<Item> WEAPONS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static SpecialSwordItem createSwordItem(Tier tier, int dmg, float aspd, Item.Properties properties) {
        SpecialSwordItem item = new SpecialSwordItem(tier, dmg, aspd, 10, properties) {

        };
        return item;
    }

    public static final RegistryObject<? extends Item> STEEL_SWORD = WEAPONS.register("steel_sword",
            () -> new ModifiableSwordItem(Tiers.DIAMOND, 7, 0.9F, 10, new Item.Properties())
    );

    public static final RegistryObject<? extends Item> STEEL_AXE = WEAPONS.register("steel_axe", () ->
        new ModifiableAxeItem(Tiers.DIAMOND, 10, 1.4F, 10, new Item.Properties()));

    public static void register(IEventBus bus) {
        WEAPONS.register(bus);
    }

//    public static void test() {
//        STEEL_SWORD.get().setMaxModifierLevel(10);
//        boolean test = STEEL_SWORD.get().addModifier(ModModifiers.FIRE_MODIFIER);
//        LOGGER.info("Adding modifier: {} ", test);
//    }

}
