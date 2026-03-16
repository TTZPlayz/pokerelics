/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pokerelics.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.Block;

import net.mcreator.pokerelics.block.SteelPlatingBlock;
import net.mcreator.pokerelics.PokerelicsMod;

public class PokerelicsModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(PokerelicsMod.MODID);
	public static final DeferredBlock<Block> STEEL_PLATING;
	static {
		STEEL_PLATING = REGISTRY.register("steel_plating", SteelPlatingBlock::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}