package net.mcreator.pokerelics.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.vertex.PoseStack;

import net.mcreator.pokerelics.world.inventory.StarterRelicGUIMenu;
import net.mcreator.pokerelics.procedures.StarterRelicGUISprayerTextProcedure;
import net.mcreator.pokerelics.procedures.StarterRelicGUICandleTextProcedure;
import net.mcreator.pokerelics.procedures.StarterRelicGUIBulbTextProcedure;
import net.mcreator.pokerelics.procedures.BeginAdventureDisplayConditionProcedure;
import net.mcreator.pokerelics.network.StarterRelicGUIButtonMessage;
import net.mcreator.pokerelics.init.PokerelicsModScreens;

import java.util.stream.Collectors;
import java.util.Arrays;

import com.mojang.blaze3d.systems.RenderSystem;

public class StarterRelicGUIScreen extends AbstractContainerScreen<StarterRelicGUIMenu> implements PokerelicsModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_begin_your_adventure;
	private ImageButton imagebutton_vigor_candle_unlit;
	private ImageButton imagebutton_absorb_bulb;
	private ImageButton imagebutton_shell_sprayer_full;

	public StarterRelicGUIScreen(StarterRelicGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 396;
		this.imageHeight = 192;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		boolean customTooltipShown = false;
		if (mouseX > leftPos + 60 && mouseX < leftPos + 124 && mouseY > topPos + 81 && mouseY < topPos + 145) {
			String hoverText = StarterRelicGUIBulbTextProcedure.execute();
			if (hoverText != null) {
				guiGraphics.renderComponentTooltip(font, Arrays.stream(hoverText.split("\n")).map(Component::literal).collect(Collectors.toList()), mouseX, mouseY);
			}
			customTooltipShown = true;
		}
		if (mouseX > leftPos + 168 && mouseX < leftPos + 232 && mouseY > topPos + 81 && mouseY < topPos + 145) {
			String hoverText = StarterRelicGUICandleTextProcedure.execute(entity);
			if (hoverText != null) {
				guiGraphics.renderComponentTooltip(font, Arrays.stream(hoverText.split("\n")).map(Component::literal).collect(Collectors.toList()), mouseX, mouseY);
			}
			customTooltipShown = true;
		}
		if (mouseX > leftPos + 276 && mouseX < leftPos + 340 && mouseY > topPos + 90 && mouseY < topPos + 154) {
			String hoverText = StarterRelicGUISprayerTextProcedure.execute();
			if (hoverText != null) {
				guiGraphics.renderComponentTooltip(font, Arrays.stream(hoverText.split("\n")).map(Component::literal).collect(Collectors.toList()), mouseX, mouseY);
			}
			customTooltipShown = true;
		}
		if (!customTooltipShown)
			this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.scale(1.5F, 1.5F, 1.0F);  // Adjust scale factor (e.g., 0.5F for smaller, 2.0F for larger)
		guiGraphics.drawString(this.font, Component.translatable("gui.pokerelics.starter_relic_gui.label_choose_your"), 66, 20, -1, false);
		poseStack.popPose();
	}

	@Override
	public void init() {
		super.init();
		button_begin_your_adventure = new PlainTextButton(this.leftPos + 146, this.topPos + 160, 135, 40, Component.translatable("gui.pokerelics.starter_relic_gui.button_begin_your_adventure"), e -> {
			int x = StarterRelicGUIScreen.this.x;
			int y = StarterRelicGUIScreen.this.y;
			if (BeginAdventureDisplayConditionProcedure.execute(entity)) {
				PacketDistributor.sendToServer(new StarterRelicGUIButtonMessage(0, x, y, z));
				StarterRelicGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}, this.font);
		this.addRenderableWidget(button_begin_your_adventure);
		imagebutton_vigor_candle_unlit = new ImageButton(this.leftPos + 170, this.topPos + 74, 64, 64,
				new WidgetSprites(ResourceLocation.parse("pokerelics:textures/screens/vigor_candle_unlit.png"), ResourceLocation.parse("pokerelics:textures/screens/vigor_candle_selected.png")), e -> {
					int x = StarterRelicGUIScreen.this.x;
					int y = StarterRelicGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new StarterRelicGUIButtonMessage(1, x, y, z));
						StarterRelicGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_vigor_candle_unlit);
		imagebutton_absorb_bulb = new ImageButton(this.leftPos + 62, this.topPos + 83, 64, 64,
				new WidgetSprites(ResourceLocation.parse("pokerelics:textures/screens/absorb_bulb.png"), ResourceLocation.parse("pokerelics:textures/screens/absorb_bulb_selected.png")), e -> {
					int x = StarterRelicGUIScreen.this.x;
					int y = StarterRelicGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new StarterRelicGUIButtonMessage(2, x, y, z));
						StarterRelicGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_absorb_bulb);
		imagebutton_shell_sprayer_full = new ImageButton(this.leftPos + 278, this.topPos + 83, 64, 64,
				new WidgetSprites(ResourceLocation.parse("pokerelics:textures/screens/shell_sprayer.png"), ResourceLocation.parse("pokerelics:textures/screens/shell_sprayer_selected.png")), e -> {
					int x = StarterRelicGUIScreen.this.x;
					int y = StarterRelicGUIScreen.this.y;
					if (true) {
						PacketDistributor.sendToServer(new StarterRelicGUIButtonMessage(3, x, y, z));
						StarterRelicGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		this.addRenderableWidget(imagebutton_shell_sprayer_full);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_begin_your_adventure.visible = BeginAdventureDisplayConditionProcedure.execute(entity);
	}
}