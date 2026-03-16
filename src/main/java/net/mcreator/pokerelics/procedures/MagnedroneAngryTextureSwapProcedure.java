package net.mcreator.pokerelics.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.pokerelics.entity.MagnedroneEntity;

public class MagnedroneAngryTextureSwapProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof MagnedroneEntity _datEntL0 && _datEntL0.getEntityData().get(MagnedroneEntity.DATA_angered)) == true) {
			if (entity instanceof MagnedroneEntity customEntity)
				customEntity.setTexture("magnedrone_angry");
		}
	}
}