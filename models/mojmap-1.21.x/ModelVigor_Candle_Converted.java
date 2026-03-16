// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class ModelVigor_Candle_Converted<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "vigor_candle_converted"), "main");
	private final ModelPart group;
	private final ModelPart candle;
	private final ModelPart holder;

	public ModelVigor_Candle_Converted(ModelPart root) {
		this.group = root.getChild("group");
		this.candle = this.group.getChild("candle");
		this.holder = this.group.getChild("holder");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition group = partdefinition.addOrReplaceChild("group", CubeListBuilder.create(),
				PartPose.offset(-2.0F, 12.0F, 1.0F));

		PartDefinition candle = group.addOrReplaceChild("candle",
				CubeListBuilder.create().texOffs(0, 6)
						.addBox(-2.0F, -6.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-3.0F, -2.0F, -3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 12)
						.addBox(-2.0F, -3.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.0F, 12.0F, -2.0F));

		PartDefinition cube_r1 = candle.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -8.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = candle.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -8.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition holder = group.addOrReplaceChild("holder",
				CubeListBuilder.create().texOffs(16, 0)
						.addBox(-3.0F, -1.0F, -2.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(8, 6)
						.addBox(-3.0F, -1.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(8, 11)
						.addBox(1.0F, -1.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.0F, 9.0F, -3.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		group.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}