package com.mewo.mewosgear;

import com.mewo.mewosgear.registry.Item.ModWeapons;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.mewo.mewosgear.Main.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventSubscriber {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
//        ModWeapons.test();
    }
}
