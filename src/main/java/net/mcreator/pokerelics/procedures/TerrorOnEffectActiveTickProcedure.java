package net.mcreator.pokerelics.procedures;

import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Minecraft;

import net.mcreator.pokerelics.init.PokerelicsModMobEffects;

import javax.annotation.Nullable;

@EventBusSubscriber(value = Dist.CLIENT)
public class TerrorOnEffectActiveTickProcedure {
	public static ViewportEvent.ComputeCameraAngles provider = null;

	public static void setAngles(float yaw, float pitch, float roll) {
		provider.setYaw(yaw);
		provider.setPitch(pitch);
		provider.setRoll(roll);
	}

	@SubscribeEvent
	public static void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
		provider = event;
		ClientLevel level = Minecraft.getInstance().level;
		Entity entity = provider.getCamera().getEntity();
		if (level != null && entity != null) {
			Vec3 entPos = entity.getPosition((float) provider.getPartialTick());
			execute(provider, entity);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(PokerelicsModMobEffects.TERROR)) {
			if (Math.random() < 0.5) {
				setAngles((float) (Minecraft.getInstance().gameRenderer.getMainCamera().getYRot() + 0.5), Minecraft.getInstance().gameRenderer.getMainCamera().getXRot(), 0);
				setAngles((float) (Minecraft.getInstance().gameRenderer.getMainCamera().getYRot() - 0.5), Minecraft.getInstance().gameRenderer.getMainCamera().getXRot(), 0);
			}
		}
	}
}