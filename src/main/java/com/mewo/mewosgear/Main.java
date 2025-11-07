package com.mewo.mewosgear;

import com.mewo.mewosgear.registry.Item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Main.MOD_ID)
public class Main {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "mewosgear";

    public Main(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();

        ModItems.register(modBus);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.STEEL_INGOT);
        }
    }

}
