package com.mewo.mewosgear.content.registry;

import com.mewo.mewosgear.content.item.ModifierItem;
import com.mewo.mewosgear.content.modifiers.IModifier;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.Map;

import static com.mewo.mewosgear.Main.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () ->
            new Item(new Item.Properties()));

    public static final RegistryObject<Item> FIRE_MODIFIER = ITEMS.register("fire_modifier", () ->
            new ModifierItem(new Item.Properties(), ModModifiers.FIRE_MODIFIER));

    public static final RegistryObject<Item> POISON_MODIFIER = ITEMS.register("poison_modifier", () ->
        new ModifierItem(new Item.Properties(), ModModifiers.POISON_MODIFIER));

    public static final RegistryObject<Item> WITHER_MODIFIER = ITEMS.register("wither_modifier", () ->
        new ModifierItem(new Item.Properties(), ModModifiers.WITHER_MODIFIER));

    public static final RegistryObject<Item> LIFESTEAL_MODIFIER = ITEMS.register("lifesteal_modifier", () ->
            new ModifierItem(new Item.Properties(), ModModifiers.LIFESTEAL_MODIFIER));

    public static final RegistryObject<Item> SLOWNESS_MODIFIER = ITEMS.register("slowness_modifier", () ->
            new ModifierItem(new Item.Properties(), ModModifiers.SLOWNESS_MODIFIER));

    public static final RegistryObject<Item> VOLATILE_MODIFIER = ITEMS.register("volatile_modifier", () ->
            new ModifierItem(new Item.Properties(), ModModifiers.VOLATILE_MODIFIER));

    public static final RegistryObject<Item> RECKLESS_MODIFIER = ITEMS.register("reckless_modifier", () ->
            new ModifierItem(new Item.Properties(), ModModifiers.RECKLESS_MODIFIER));

    public static final RegistryObject<Item> THUNDER_MODIFIER = ITEMS.register("thunder_modifier", () ->
            new ModifierItem(new Item.Properties(), ModModifiers.THUNDER_MODIFIER));

    public static final RegistryObject<Item> GLUTTON_MODIFIER = ITEMS.register("glutton_modifier", () ->
            new ModifierItem(new Item.Properties(), ModModifiers.GLUTTON_MODIFIER));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }


}
