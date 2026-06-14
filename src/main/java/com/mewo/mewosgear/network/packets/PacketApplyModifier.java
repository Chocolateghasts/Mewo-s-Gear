package com.mewo.mewosgear.network.packets;

import com.mewo.mewosgear.content.block.functional.toolmodificationtable.MenuToolModificationTable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketApplyModifier {
    public static void encode(PacketApplyModifier msg, FriendlyByteBuf byteBuf) {}
    public static PacketApplyModifier decode(FriendlyByteBuf byteBuf) { return new PacketApplyModifier(); }

    public static void handle(PacketApplyModifier msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player.containerMenu instanceof MenuToolModificationTable menu) {
                menu.applyModifier(player);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
