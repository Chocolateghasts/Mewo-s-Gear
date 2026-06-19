package com.mewo.mewosgear;

import com.mewo.mewosgear.content.registry.*;
import com.mewo.mewosgear.network.ModNetworkHandler;
import com.mewo.mewosgear.util.file.json.JsonUtil;
import com.mewo.mewosgear.util.nodenetwork.NodeNetworkEnums;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.io.File;

@Mod(Main.MOD_ID)
public class Main {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "mewosgear";

    public Main(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        ModItems.register(bus);
        ModWeapons.register(bus);
        ModTools.register(bus);
        ModBlocks.register(bus);
        ModBlockEntities.register(bus);
        ModMenuTypes.register(bus);
        ModEffects.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::addCreative);
    }

    public static void testFileHandling() {
        File file = EventSubscriber.getWorldFile();
        if (file != null) {
            File targetFile = new File(file, "Output.txt");
            NodeNetworkEnums.Node node = new NodeNetworkEnums.Node(0);
            JsonUtil.write(targetFile, node);
        }
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
