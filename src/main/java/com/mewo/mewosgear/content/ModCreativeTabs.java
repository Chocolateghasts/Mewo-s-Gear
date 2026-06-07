package com.mewo.mewosgear.content;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.content.registry.ModItems;
import com.mewo.mewosgear.content.registry.ModWeapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TAB.register("mewosgear_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModWeapons.STEEL_SWORD.get()))
                    .title(Component.translatable("creativetab.mewosgear_tab"))
                    .displayItems((pParamters, pOutput) -> {
                        pOutput.accept(ModWeapons.STEEL_SWORD.get());
                        pOutput.accept(ModItems.STEEL_INGOT.get());
                    }).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TAB.register(bus);
    }



}


