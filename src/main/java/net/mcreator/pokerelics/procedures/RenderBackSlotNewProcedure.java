package net.mcreator.pokerelics.procedures;

import org.joml.Vector3f;

import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.EntityModel;

import net.mcreator.pokerelics.network.PokerelicsModVariables;
import net.mcreator.pokerelics.init.PokerelicsModItems;
import net.mcreator.pokerelics.init.PokerelicsModHumanoidModels;

import javax.annotation.Nullable;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

@EventBusSubscriber(Dist.CLIENT)
public class RenderBackSlotNewProcedure {
	@SubscribeEvent
	public static void onPlayerRendered(RenderPlayerEvent.Pre event) {
		execute(event, event.getEntity(), event);
	}

	public static void offsetScale(PlayerModel model, Vector3f offset) {
		model.head.offsetScale(offset);
		model.head.y += offset.x() > 0 ? 0.05 : -0.05;
		model.body.offsetScale(offset);
		model.leftArm.offsetScale(offset);
		model.rightArm.offsetScale(offset);
		model.leftLeg.offsetScale(offset);
		model.rightLeg.offsetScale(offset);
		model.hat.offsetScale(offset);
		model.hat.y += offset.x() > 0 ? 0.05 : -0.05;
		model.jacket.offsetScale(offset);
		model.leftSleeve.offsetScale(offset);
		model.rightSleeve.offsetScale(offset);
		model.leftPants.offsetScale(offset);
		model.rightPants.offsetScale(offset);
	}

	public static Collection<Runnable> capes = new ConcurrentLinkedQueue<>();

	public static void renderHumanoid(RenderPlayerEvent playerRenderEvent, PlayerModel model, VertexConsumer vertexConsumer) {
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		((HumanoidModel) playerRenderEvent.getRenderer().getModel()).copyPropertiesTo(model);
		AbstractClientPlayer eventEntity_ = (AbstractClientPlayer) playerRenderEvent.getEntity();
		float partialTick = playerRenderEvent.getPartialTick();
		model.attackTime = eventEntity_.getAttackAnim(partialTick);
		float limbSwing = eventEntity_.walkAnimation.position(partialTick);
		float limbSwingAmount = eventEntity_.walkAnimation.speed(partialTick);
		float ageInTicks = eventEntity_.tickCount + partialTick;
		float interpolatedBodyYaw = Mth.rotLerp(partialTick, eventEntity_.yBodyRotO, eventEntity_.yBodyRot);
		float interpolatedHeadYaw = Mth.rotLerp(partialTick, eventEntity_.yHeadRotO, eventEntity_.yHeadRot);
		float netHeadYaw = interpolatedHeadYaw - interpolatedBodyYaw;
		float headPitch = Mth.lerp(partialTick, eventEntity_.xRotO, eventEntity_.getXRot());
		poseStack.pushPose();
		model.prepareMobModel(eventEntity_, limbSwing, limbSwingAmount, partialTick);
		CompoundTag playerData = eventEntity_.getPersistentData();
		float oldAnimationProgress = 0;
		float oldAgeInTicks = 0;
		if (playerData.contains("PlayerAnimationProgress")) {
			oldAnimationProgress = playerData.getFloat("PlayerAnimationProgress");
			oldAgeInTicks = playerData.getFloat("LastTickTime");
		}
		model.setupAnim(eventEntity_, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (playerData.contains("PlayerAnimationProgress") && playerData.getFloat("PlayerAnimationProgress") > 0) {
			playerData.putFloat("PlayerAnimationProgress", oldAnimationProgress);
			playerData.putFloat("LastTickTime", oldAgeInTicks);
		} else if (oldAnimationProgress > 0) {
			model.setupAnim(eventEntity_, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
		playerRenderEvent.getRenderer().setupRotations(eventEntity_, poseStack, ageInTicks, interpolatedBodyYaw, partialTick, 0);
		poseStack.scale(-0.938f, -0.938f, 0.938f);
		poseStack.translate(0.0D, -1.501, 0.0D);
		Vector3f offset = new Vector3f(0.015f);
		offsetScale(model, offset);
		if (!capes.isEmpty()) {
			capes.forEach(cape -> cape.run());
			capes.clear();
		}
		model.renderToBuffer(poseStack, vertexConsumer, playerRenderEvent.getPackedLight(), LivingEntityRenderer.getOverlayCoords(eventEntity_, 0));
		offset.negate();
		offsetScale(model, offset);
		poseStack.popPose();
	}

	public static void renderEntity(RenderPlayerEvent playerRenderEvent, EntityModel model, VertexConsumer vertexConsumer) {
		PoseStack poseStack = playerRenderEvent.getPoseStack();
		playerRenderEvent.getRenderer().getModel().copyPropertiesTo(model);
		AbstractClientPlayer eventEntity_ = (AbstractClientPlayer) playerRenderEvent.getEntity();
		float partialTick = playerRenderEvent.getPartialTick();
		float limbSwing = eventEntity_.walkAnimation.position(partialTick);
		float limbSwingAmount = eventEntity_.walkAnimation.speed(partialTick);
		float ageInTicks = eventEntity_.tickCount + partialTick;
		float interpolatedBodyYaw = Mth.rotLerp(partialTick, eventEntity_.yBodyRotO, eventEntity_.yBodyRot);
		float interpolatedHeadYaw = Mth.rotLerp(partialTick, eventEntity_.yHeadRotO, eventEntity_.yHeadRot);
		float netHeadYaw = interpolatedHeadYaw - interpolatedBodyYaw;
		float headPitch = Mth.lerp(partialTick, eventEntity_.xRotO, eventEntity_.getXRot());
		poseStack.pushPose();
		playerRenderEvent.getRenderer().setupRotations(eventEntity_, poseStack, ageInTicks, interpolatedBodyYaw, partialTick, 0);
		poseStack.scale(-0.938f, -0.938f, 0.938f);
		poseStack.translate(0.0D, -1.501, 0.0D);
		model.prepareMobModel(eventEntity_, limbSwing, limbSwingAmount, partialTick);
		model.setupAnim(eventEntity_, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		model.renderToBuffer(poseStack, vertexConsumer, playerRenderEvent.getPackedLight(), LivingEntityRenderer.getOverlayCoords(eventEntity_, 0));
		poseStack.popPose();
	}

	public static void execute(Entity entity, RenderPlayerEvent playerRenderEvent) {
		execute(null, entity, playerRenderEvent);
	}

	private static void execute(@Nullable Event event, Entity entity, RenderPlayerEvent playerRenderEvent) {
		if (entity == null || playerRenderEvent == null)
			return;
		if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.is(ItemTags.create(ResourceLocation.parse("pokerelics:relics")))) {
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.BULB_OF_ABSORPTION.get()) {
				{
					ResourceLocation texture = PokerelicsModVariables.bulb_texture;
					renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.BULB_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
				}
			}
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.VIGOR_CANDLE.get()) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).candle_activation_timer > 0) {
					{
						ResourceLocation texture = PokerelicsModVariables.candle_texture_lit;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.CANDLE_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else {
					{
						ResourceLocation texture = PokerelicsModVariables.candle_texture_unlit;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.CANDLE_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				}
			}
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SHELL_SPRAYER.get()) {
				if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 1) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_1;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 2) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_2;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 3) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_3;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 4) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_4;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 5) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_5;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 6) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_6;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 7) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_7;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 8) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_8;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 9) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_9;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 10) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_10;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 11) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_11;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 12) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_12;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 13) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_13;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				} else if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).texture_timer < 14) {
					{
						ResourceLocation texture = PokerelicsModVariables.sprayer_full_14;
						renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.SPRAYER_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
					}
				}
			}
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.REGAL_AMULET.get()) {
				{
					ResourceLocation texture = PokerelicsModVariables.regal_amulet;
					renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.AMULET_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
				}
			}
			if (entity.getData(PokerelicsModVariables.PLAYER_VARIABLES).backSlot.getItem() == PokerelicsModItems.SINISTER_GLOVE.get()) {
				{
					ResourceLocation texture = PokerelicsModVariables.sinister_glove;
					renderHumanoid(playerRenderEvent, PokerelicsModHumanoidModels.GLOVE_MODEL, playerRenderEvent.getMultiBufferSource().getBuffer(RenderType.armorCutoutNoCull(texture)));
				}
			}
		}
	}
}