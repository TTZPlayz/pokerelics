package net.mcreator.pokerelics.procedures;

import org.checkerframework.checker.units.qual.A;

import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.component.DataComponents;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModParticleTypes;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nullable;

import java.util.Comparator;

@EventBusSubscriber
public class RelicTimersProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double particleRadius = 0;
		double particleAmount = 0;
		double particleDX = 0;
		double yAmount = 0;
		double particleDY = 0;
		double A = 0;
		double zAmount = 0;
		double particleDZ = 0;
		double H = 0;
		double XAng = 0;
		double particleCount = 0;
		double O = 0;
		double sprayY = 0;
		double sprayZ = 0;
		double XAmount = 0;
		double coneLength = 0;
		double coneAngle = 0;
		double sprayX = 0;
		double CenterZ = 0;
		double CenterY = 0;
		double CenterX = 0;
		double Strength = 0;
		ItemStack oldBackSlot = ItemStack.EMPTY;
		if (!(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == ItemStack.EMPTY.getItem())) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
				if (entity.isShiftKeyDown()) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("\u00A7o\u00A7lPhotosynthesizing...\u00A7r \u00A72"
								+ ((new java.text.DecimalFormat("#.#").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("heal_counter") / 20) + "/")
										+ "" + (new java.text.DecimalFormat("##").format(PokerelicsModVariables.max_bulb_cooldown / 20) + "s\u00A7r")))),
								true);
				}
			} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") > 0) {
					{
						final String _tagName = "activation_timer";
						final double _tagValue = (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					particleAmount = 4;
					particleRadius = 0.25;
					for (int index0 = 0; index0 < (int) particleAmount; index0++) {
						world.addParticle((SimpleParticleType) (PokerelicsModParticleTypes.VIGOR_FLAME.get()), (x + 0 + Mth.nextDouble(RandomSource.create(), -0.1, 0.1) * particleRadius),
								(y + 1 + Mth.nextDouble(RandomSource.create(), 0, 0) * particleRadius), (z + 0 + Mth.nextDouble(RandomSource.create(), -0.1, 0.1) * particleRadius), (Mth.nextDouble(RandomSource.create(), -0.1, 0.1)), 0,
								(Mth.nextDouble(RandomSource.create(), -0.1, 0.1)));
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("\u00A7o\u00A7lFirestreak:\u00A7r \u00A76"
								+ (new java.text.DecimalFormat("#.#").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_timer") / 20)
										+ "s\u00A7r"))),
								true);
				} else {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.firestreak_counter = 0;
						_vars.markSyncDirty();
					}
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("extinguish_timer") > 0) {
					{
						final String _tagName = "extinguish_timer";
						final double _tagValue = (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("extinguish_timer") - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
				}
			} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("spray_countdown") > 0) {
					{
						final String _tagName = "spray_countdown";
						final double _tagValue = (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("spray_countdown") - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("spray_countdown") <= 300) {
					if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("spray_countdown") > 240) {
						coneAngle = 5;
						particleCount = 5;
						coneLength = 3;
						Entity player = entity;
						Vec3 eyePos = player.getEyePosition();
						Vec3 lookVec = player.getViewVector(1.0F);
						// Get player's yaw and pitch
						float yaw = player.getYRot();
						float pitch = player.getXRot();
						double maxSpreadRad = Math.toRadians(coneAngle);
						for (int i = 0; i < particleCount; i++) {
							// Random distance
							double distance = 0.5 + Math.random() * (coneLength - 0.5);
							// Random spread within cone
							double spreadYaw = (Math.random() - 0.5) * maxSpreadRad * 2;
							double spreadPitch = (Math.random() - 0.5) * maxSpreadRad * 2;
							// Calculate direction with spread
							float finalYaw = (float) Math.toRadians(yaw + Math.toDegrees(spreadYaw));
							float finalPitch = (float) Math.toRadians(pitch + Math.toDegrees(spreadPitch));
							// Convert to direction vector
							double horizontalDist = Math.cos(finalPitch);
							double vx = -Math.sin(finalYaw) * horizontalDist;
							double vy = -Math.sin(finalPitch);
							double vz = Math.cos(finalYaw) * horizontalDist;
							Vec3 direction = new Vec3(vx, vy, vz).normalize();
							// Particle position
							Vec3 particlePos = eyePos.add(direction.scale(distance));
							// Particle velocity (moves outward)
							double velocityMult = 0.03;
							sprayX = particlePos.x;
							sprayY = particlePos.y;
							sprayZ = particlePos.z;
							particleDX = direction.x * velocityMult;
							particleDY = direction.y * velocityMult;
							particleDZ = direction.z * velocityMult;
						}
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.SPLASH, sprayX, sprayY, sprayZ, 5, 0.2, 0.2, 0.2, 1);
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.BUBBLE, sprayX, sprayY, sprayZ, 5, particleDX, particleDY, particleDZ, 1);
						Strength = 1.5;
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
									}
								}
							}
						}
					}
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("fill_level") < 9) {
					if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("spray_countdown") % 30 == 0) {
						{
							final String _tagName = "fill_level";
							final double _tagValue = (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("fill_level") + 1);
							CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
						}
					}
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 14) {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.texture_timer = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer + 0.5;
						_vars.markSyncDirty();
					}
				} else {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.texture_timer = 0.5;
						_vars.markSyncDirty();
					}
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("fill_level") == 9
						&& entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).sent_already == false) {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.sent_already = true;
						_vars.markSyncDirty();
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal("\u00A7o\u00A7lFull!\u00A7r"), true);
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("fill_level") < 9) {
					if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).sent_already == true) {
						{
							PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
							_vars.sent_already = false;
							_vars.markSyncDirty();
						}
					}
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal(("\u00A7o\u00A7lRefilling...\u00A7r\u00A73"
								+ (new java.text.DecimalFormat("#").format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("fill_level"))
										+ "/9\u00A7r"))),
								true);
				}
			} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SINISTER_GLOVE.get()) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") > 0) {
					{
						final String _tagName = "activation_cooldown";
						final double _tagValue = (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") / 20 <= 10) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A75" + (new java.text.DecimalFormat("#.#")
									.format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") / 20) + "s\u00A7r"))), true);
					} else {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A75" + (new java.text.DecimalFormat("##")
									.format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") / 20) + "s\u00A7r"))), true);
					}
				}
			} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.REGAL_AMULET.get()) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") > 0) {
					{
						final String _tagName = "activation_cooldown";
						final double _tagValue = (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") - 1);
						CustomData.update(DataComponents.CUSTOM_DATA, entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot, tag -> tag.putDouble(_tagName, _tagValue));
					}
					if (particleRadius / 20 <= 10) {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A7e" + (new java.text.DecimalFormat("#.#")
									.format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") / 20) + "s\u00A7r"))), true);
					} else {
						if (entity instanceof Player _player && !_player.level().isClientSide())
							_player.displayClientMessage(Component.literal(("\u00A7o\u00A7lCooldown:\u00A7r \u00A7e" + (new java.text.DecimalFormat("##")
									.format(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("activation_cooldown") / 20) + "s\u00A7r"))), true);
					}
				}
			}
			if (!(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("attuned_player")).equals("")
					&& !(entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("attuned_player")).equals(entity.getDisplayName().getString())) {
				oldBackSlot = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.copy();
				{
					PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
					_vars.backSlot = ItemStack.EMPTY.copy();
					_vars.markSyncDirty();
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = oldBackSlot.copy();
					_setstack.setCount(1);
					ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
				}
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("This Relic is attuned to " + (oldBackSlot.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("attuned_player") + "!"))), true);
			}
		}
	}
}