package com.mewo.mewosgear.content.registry;

import com.mewo.mewosgear.Main;
import com.mewo.mewosgear.content.block.functional.tool_modification_table.BlockEntityToolModificationTable;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    public static final RegistryObject<BlockEntityType<BlockEntityToolModificationTable>> TOOL_MODIFICATION_TABLE_BE
            = BLOCK_ENTITIES.register("tool_modification_table_be", () ->
                    BlockEntityType.Builder.of(BlockEntityToolModificationTable::new,
                            ModBlocks.TOOL_MODIFICATION_TABLE.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

}
