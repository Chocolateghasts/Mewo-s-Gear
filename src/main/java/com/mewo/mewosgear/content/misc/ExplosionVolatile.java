//package com.mewo.mewosgear.content.misc;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.level.Explosion;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class ExplosionVolatile extends Explosion {
//    public ExplosionVolatile(Level level, @Nullable Entity source, double x, double y, double z, boolean isFire, float radius, List<BlockPos> positions) {
//        super(level, source, x, y, z, radius, positions);
//    }
//
//    @Override
//    public void explode() {
//        super.explode();
//        this.clearToBlow();
//
//    }
//
//    @Override
//    public void finalizeExplosion(boolean hasParticles) {
//        super.finalizeExplosion(hasParticles);
//    }
//}
