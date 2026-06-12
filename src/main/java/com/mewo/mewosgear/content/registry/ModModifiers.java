package com.mewo.mewosgear.content.registry;

import com.mewo.mewosgear.content.modifiers.IModifier;
import com.mewo.mewosgear.content.modifiers.ModifierEnums;
import com.mewo.mewosgear.content.modifiers.SimpleModifier;
import com.mewo.mewosgear.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierCategory.*;
import static com.mewo.mewosgear.content.modifiers.ModifierEnums.ModifierToolType.*;

public class ModModifiers {
    private static final Log log = LogFactory.getLog(ModModifiers.class);
    public static Map<String, IModifier> modifiers = new HashMap<String, IModifier>();
    // TODO: add tier upgrade stuff
    public static IModifier createModifier(String name, int tier, ModifierEnums.ModifierCategory category) {
        IModifier modifier = new SimpleModifier(name, tier, category, WEAPON, 0xFFFFFF);
        modifiers.put(name, modifier);
        return modifier;
    }

    private static Random rng = new Random();

    public static IModifier getModifier(String name) {
        return modifiers.get(name);
    }

    public static final IModifier FIRE_MODIFIER = new SimpleModifier("fire_modifier", 1, ON_HIT, WEAPON, 0xFF4500) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            target.setSecondsOnFire(5);
        }
    };

    public static final IModifier POISON_MODIFIER = new SimpleModifier("poison_modifier", 1, ON_HIT, WEAPON, 0x4E9A00) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            target.addEffect(new MobEffectInstance(ModEffects.TRUE_POISON.get(), 360));
        }
    };

    public static final IModifier WITHER_MODIFIER = new SimpleModifier("wither_modifier", 2, ON_HIT, WEAPON, 0x2D2D2D) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 360));
        }
    };

    public static final IModifier LIFESTEAL_MODIFIER = new SimpleModifier("lifesteal_modifier", 2, ON_HIT, WEAPON, 0xCC0000) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            attacker.heal(1.0F);
        }
    };

    public static final IModifier SLOWNESS_MODIFIER = new SimpleModifier("slowness_modifier", 1, ON_HIT, WEAPON, 0x6495ED) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN));
        }
    };

    public static final IModifier VOLATILE_MODIFIER = new SimpleModifier("volatile_modifier", 1, ON_HIT, WEAPON, 0xFFAA00) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!target.level().isClientSide) {
                if (rng.nextInt(0, 10) <= 4) {

                    target.level().explode(attacker, target.getX(), target.getY(), target.getZ(), 2.5F, false, Level.ExplosionInteraction.NONE);
                    // Optional: Add cooldown or additional effects here
                    // stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        }
    };

    public static final IModifier RECKLESS_MODIFIER = new SimpleModifier("reckless_modifier", 2, ON_HIT, WEAPON, 0xFF6600) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            attacker.hurt(attacker.damageSources().mobAttack(target), 2);
            target.hurt(target.damageSources().mobAttack(attacker), 2);
        }
    };

    public static final IModifier THUNDER_MODIFIER = new SimpleModifier("thunder_modifier", 3, ON_HIT, WEAPON, 0xFFFF33) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (target.level().isClientSide) return;
            BlockPos pos = target.getOnPos();
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(target.level());
            assert lightningBolt != null;
            lightningBolt.moveTo(pos, 0, 0);
            target.level().addFreshEntity(lightningBolt);
        }
    };

    public static final IModifier GLUTTON_MODIFIER = new SimpleModifier("glutton_modifier", 3, ON_HIT, WEAPON, 0x8B4513) {
        @Override
        public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (attacker instanceof Player) {
                Player player = (Player) attacker;
                FoodData foodData = player.getFoodData();
                if (foodData.getFoodLevel() > 0) {
                    foodData.setFoodLevel(foodData.getFoodLevel() - 1);
                } else {
                    player.hurt(player.damageSources().starve(), 2);
                }
                target.hurt(target.damageSources().playerAttack(player), 2);
            }
        }
    };



    public static final IModifier VEIN_MINER_MODIFIER = new SimpleModifier("vein_miner_modifier", 2, ON_BREAK, TOOL, 0xFFFFFF) {
        @Override
        public void onBreak(ItemStack stack, Level level, BlockState blockState, BlockPos pos, LivingEntity player) {
            if (level.isClientSide) return;
            Set<BlockPos> blocks = MathUtil.getAllAdjacentBlocks(pos, 10, level, blockState);
            for (BlockPos pos1 : blocks) {
                BlockState blockState1 = level.getBlockState(pos1);
                boolean success = level.destroyBlock(pos1, false, player);
                if (success && player instanceof ServerPlayer) {
                    for (ItemStack drop : Block.getDrops(blockState1, (ServerLevel) level, pos1, null)) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        serverPlayer.addItem(drop);
                    }
                }

            }
        }
    };

    static {
        modifiers.put(FIRE_MODIFIER.getName(), FIRE_MODIFIER);
        modifiers.put(POISON_MODIFIER.getName(), POISON_MODIFIER);
        modifiers.put(WITHER_MODIFIER.getName(), WITHER_MODIFIER);
        modifiers.put(LIFESTEAL_MODIFIER.getName(), LIFESTEAL_MODIFIER);
        modifiers.put(SLOWNESS_MODIFIER.getName(), SLOWNESS_MODIFIER);
        modifiers.put(VOLATILE_MODIFIER.getName(), VOLATILE_MODIFIER);
        modifiers.put(RECKLESS_MODIFIER.getName(), RECKLESS_MODIFIER);
        modifiers.put(THUNDER_MODIFIER.getName(), THUNDER_MODIFIER);
        modifiers.put(GLUTTON_MODIFIER.getName(), GLUTTON_MODIFIER);
        modifiers.put(VEIN_MINER_MODIFIER.getName(), VEIN_MINER_MODIFIER);
    }
}
