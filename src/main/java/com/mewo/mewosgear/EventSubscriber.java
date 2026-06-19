package com.mewo.mewosgear;

import com.mewo.mewosgear.util.file.json.JsonUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

import static com.mewo.mewosgear.Main.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventSubscriber {
    private static File worldFile;

    public static File getWorldFile() {
        return worldFile;
    }

    public static void setWorldFile(File worldFile) {
        EventSubscriber.worldFile = worldFile;
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        File worldFile = JsonUtil.getWorldFilePath(server);
        setWorldFile(worldFile);
        Main.testFileHandling();
    }
}

