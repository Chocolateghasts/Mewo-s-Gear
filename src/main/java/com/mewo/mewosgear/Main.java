package com.mewo.mewosgear;

import com.mewo.mewosgear.content.registry.ModBlockEntities;
import com.mewo.mewosgear.content.registry.ModBlocks;
import com.mewo.mewosgear.content.registry.ModMenuTypes;
import com.mewo.mewosgear.network.ModNetworkHandler;
import com.mewo.mewosgear.content.registry.ModItems;
import com.mewo.mewosgear.content.registry.ModWeapons;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Main.MOD_ID)
public class Main {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "mewosgear";

    public Main(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        ModItems.register(bus);
        ModBlocks.register(bus);
        ModWeapons.register(bus);
        ModBlockEntities.register(bus);
        ModMenuTypes.register(bus);

        bus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetworkHandler::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.STEEL_INGOT);
            event.accept(ModWeapons.STEEL_SWORD);

        }
    }

}
