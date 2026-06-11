package com.mewo.mewosgear.content.registry;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.content.item.ModifiablePickaxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTools {
    public static final DeferredRegister<Item> TOOLS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> STEEL_PICKAXE = TOOLS.register("steel_pickaxe", () ->
        new ModifiablePickaxeItem(Tiers.DIAMOND, 2, 0.3F, 5, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        TOOLS.register(eventBus);
    }

}
