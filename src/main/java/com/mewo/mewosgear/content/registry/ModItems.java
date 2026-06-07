package com.mewo.mewosgear.content.registry;

import com.mewo.mewosgear.content.modifiers.ModModifiers;
import com.mewo.mewosgear.content.modifiers.ModifierItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mewo.mewosgear.Main.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> POISEN_MODIFIER = ITEMS.register("poisen_modifier", () ->
        new ModifierItem(new Item.Properties(), ModModifiers.POISON_MODIFIER));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

}
