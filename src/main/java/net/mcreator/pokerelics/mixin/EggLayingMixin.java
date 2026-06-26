package net.mcreator.pokerelics.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Random;
import net.mcreator.pokerelics.init.PokerelicsModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

@Mixin(Chicken.class)
public abstract class EggLayingMixin {
	    private static final Random LUCKY_RANDOM = new Random();

    @Inject(
    method = "aiStep",
    at = @At(
        value = "HEAD",
        target = "Lnet/minecraft/world/entity/animal/Chicken;aiStep()V"
    ),
    require = 1
)
private void modifyEggDrop(CallbackInfo ci) {
    Chicken chicken = (Chicken) (Object) this;
    if (!chicken.level().isClientSide()
        && chicken.isAlive()
        && !chicken.isBaby()
        && !chicken.isChickenJockey()
        && --chicken.eggTime <= 0) {

        if (RandomSource.create().nextInt(1000) == 0) {
            chicken.spawnAtLocation(new ItemStack(PokerelicsModItems.LUCKY_EGG.get()));
            chicken.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ENTITY_PLACE);
        } else {
            chicken.spawnAtLocation(Items.EGG);
            chicken.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ENTITY_PLACE);
        }

        chicken.eggTime = chicken.getRandom().nextInt(6000) + 6000;
        chicken.playSound(SoundEvents.CHICKEN_EGG, 1.0F,
            (chicken.getRandom().nextFloat() - chicken.getRandom().nextFloat()) * 0.2F + 1.0F);
    }
}}