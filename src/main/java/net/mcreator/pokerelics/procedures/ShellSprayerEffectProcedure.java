package net.mcreator.pokerelics.procedures;

import org.lwjgl.glfw.GLFW;

import org.checkerframework.checker.units.qual.A;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nullable;

import java.util.Comparator;

@EventBusSubscriber
public class ShellSprayerEffectProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double yAmount = 0;
		double A = 0;
		double XAmount = 0;
		double zAmount = 0;
		double H = 0;
		double CenterZ = 0;
		double CenterY = 0;
		double XAng = 0;
		double CenterX = 0;
		double Strength = 0;
		double O = 0;
		double particleRadius = 0;
		double particleAmount = 0;
		if (entity instanceof Player) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
				if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
					if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("fill_level") >= 9) {
						{
							final String _tagName = "fill_level";
							final double _tagValue = 0;
							CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
						}
						{
							final String _tagName = "spray_countdown";
							final double _tagValue = 300;
							CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.splash.high_speed")), SoundSource.NEUTRAL, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.player.splash.high_speed")), SoundSource.NEUTRAL, 1, 1, false);
							}
						}
						Strength = 3;
						CenterX = entity.getX();
						CenterY = entity.getY();
						CenterZ = entity.getZ();
						{
							final Vec3 _center = new Vec3(x, y, z);
							for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList()) {
								if (!(entityiterator == entity)) {
									if (!(entityiterator.getX() == CenterX && entityiterator.getY() == CenterY && entityiterator.getZ() == CenterZ)) {
										O = Math.abs(entityiterator.getY() - CenterY);
										A = Math.sqrt(Math.pow(entityiterator.getX() - CenterX, 2) + Math.pow(entityiterator.getZ() - CenterZ, 2));
										H = Math.sqrt(Math.pow(O, 2) + Math.pow(A, 2));
										if (!(A == 0)) {
											XAmount = Math.toDegrees(Math.tan((entityiterator.getX() - CenterX) / A)) / 90;
											zAmount = Math.toDegrees(Math.tan((entityiterator.getZ() - CenterZ) / A)) / 90;
										}
										XAng = Math.sin((entityiterator.getY() - CenterY) / H);
										yAmount = XAng;
										entityiterator.setDeltaMovement(new Vec3((XAmount * Strength), (yAmount * Strength), (zAmount * Strength)));
										if (entityiterator instanceof Blaze) {
											entityiterator.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("pokerelics:splash_damage"))), entity),
													(float) (1 + PokerelicsModVariables.blaze_damage));
										} else if (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
											entityiterator.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("pokerelics:splash_damage"))), entity), 2);
											if (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") > 0) {
												{
													final String _tagName = "activation_timer";
													final double _tagValue = 0;
													CustomData.update(DataComponents.CUSTOM_DATA, entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
												}
												if (entityiterator instanceof Player _player && !_player.level().isClientSide())
													_player.displayClientMessage(Component.literal("\u00A7l\u00A77Extinguished!\u00A7r"), true);
												if (world instanceof ServerLevel _level) {
													entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.hurtAndBreak(1, _level, null, _stkprov -> {
													});
												}
												{
													final String _tagName = "extinguish_timer";
													final double _tagValue = 600;
													CustomData.update(DataComponents.CUSTOM_DATA, entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
												}
											}
										} else if (entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
											{
												final String _tagName = "heal_counter";
												final double _tagValue = 400;
												CustomData.update(DataComponents.CUSTOM_DATA, entityiterator.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
											}
										} else {
											entityiterator.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("pokerelics:splash_damage"))), entity), 1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}