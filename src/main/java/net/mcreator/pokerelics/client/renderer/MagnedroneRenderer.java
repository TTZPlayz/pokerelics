package net.mcreator.pokerelics.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.pokerelics.entity.MagnedroneEntity;
import net.mcreator.pokerelics.client.model.ModelMagnedrone;

public class MagnedroneRenderer extends MobRenderer<MagnedroneEntity, ModelMagnedrone<MagnedroneEntity>> {
	public MagnedroneRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMagnedrone<MagnedroneEntity>(context.bakeLayer(ModelMagnedrone.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(MagnedroneEntity entity) {
		return ResourceLocation.parse("pokerelics:textures/entities/" + entity.getTexture() + ".png");
	}
}