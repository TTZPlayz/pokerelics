// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class ShellSprayer<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "shellsprayer_converted"), "main");
	private final ModelPart group;
	private final ModelPart sprayer;
	private final ModelPart water;
	private final ModelPart holder;

	public ShellSprayer(ModelPart root) {
		this.group = root.getChild("group");
		this.sprayer = this.group.getChild("sprayer");
		this.water = this.group.getChild("water");
		this.holder = this.group.getChild("holder");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition group = partdefinition.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offset(0.0F, 28.0F, 0.0F));

		PartDefinition sprayer = group.addOrReplaceChild("sprayer", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 13).addBox(3.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 1.5708F));

		PartDefinition water = group.addOrReplaceChild("water", CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, -4.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 1.5708F));

		PartDefinition holder = group.addOrReplaceChild("holder", CubeListBuilder.create().texOffs(0, 7).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		group.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}