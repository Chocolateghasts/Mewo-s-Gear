package com.mewo.mewosgear.content.registry;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.content.effects.EffectTruePoison;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MOD_ID);

    public static final RegistryObject<? extends MobEffect> TRUE_POISON = EFFECTS.register("true_poison", EffectTruePoison::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
