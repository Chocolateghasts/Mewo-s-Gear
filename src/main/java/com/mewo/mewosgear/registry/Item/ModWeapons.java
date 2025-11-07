package com.mewo.mewosgear.registry.Item;

import com.mewo.mewosgear.content.Item.SpecialSwordItem;
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
        SpecialSwordItem item = new SpecialSwordItem(tier, dmg, aspd, properties) {

        };
        return item;
    }

    public static final RegistryObject<SpecialSwordItem> STEEL_SWORD = WEAPONS.register("steel_sword",
            () -> createSwordItem(Tiers.DIAMOND, 7, 0.9F, new Item.Properties()));

    public static void register(IEventBus bus) {
        WEAPONS.register(bus);
    }

}
