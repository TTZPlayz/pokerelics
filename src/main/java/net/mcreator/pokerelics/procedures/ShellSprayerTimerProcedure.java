package net.mcreator.pokerelics.procedures;

import org.checkerframework.checker.units.qual.A;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;

import javax.annotation.Nullable;

import java.util.Comparator;

@EventBusSubscriber
public class ShellSprayerTimerProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean LaunchStatus = false;
		double particleRadius = 0;
		double particleAmount = 0;
		double coneAngle = 0;
		double particleCount = 0;
		double coneLength = 0;
		double sprayX = 0;
		double sprayY = 0;
		double sprayZ = 0;
		double particleDX = 0;
		double particleDY = 0;
		double particleDZ = 0;
		double RandomZ = 0;
		double RandomX = 0;
		double Y = 0;
		double Level = 0;
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
		if (entity instanceof Player) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_spray > 0) {
					{
						PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
						_vars.time_since_spray = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_spray - 1;
						_vars.markSyncDirty();
					}
				}
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_spray <= 300) {
					if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_spray > 240) {
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
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).fill_level < 9) {
					if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).time_since_spray % 30 == 0) {
						{
							PokerelicsModVariables.PlayerVariables _vars = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES);
							_vars.fill_level = entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).fill_level + 1;
							_vars.markSyncDirty();
						}
					}
				}
			}
		}
		if (entity.getPersistentData().getDouble("splashed") > 0) {
			entity.getPersistentData().putDouble("splashed", (entity.getPersistentData().getDouble("splashed") - 1));
		}
	}
}