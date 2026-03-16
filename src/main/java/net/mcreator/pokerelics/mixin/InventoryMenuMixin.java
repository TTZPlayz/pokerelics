package net.mcreator.pokerelics.mixin;

import net.mcreator.pokerelics.network.PokerelicsModVariables;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Next;

/*
NeoForge 1.21.1:
- Adds a single "back" slot, stored in PokerelicsModVariables.PlayerVariables.backSlot.
- Places it directly above the offhand slot (player inventory index 40).
- IMPORTANT: No game-mode checks in the constructor (prevents join-time NPE).
- Hides the slot in Creative/Spectator on the client by overriding Slot#isActive(), so nothing is rendered.
*/

@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends AbstractContainerMenu {

    protected InventoryMenuMixin(MenuType<?> type, int id) {
        super(type, id);
    }

    // InventoryMenu(Inventory inventory, boolean isLocal, Player player)
    @Inject(method = "<init>(Lnet/minecraft/world/entity/player/Inventory;ZLnet/minecraft/world/entity/player/Player;)V", at = @At("TAIL"))
    private void pokerelics$addBackSlot(Inventory inventory, boolean isLocal, Player player, CallbackInfo ci) {

    int x, y;
    // Avoid double-adding if reconstructed
    if (pokerelics$findBackSlotIndex(this) != -1) return;
    
    // Survival mode positioning - above the offhand slot
    Slot offhand = pokerelics$findPlayerSlotByIndex(this, 40);
    x = offhand != null ? offhand.x : 77;  // Fallback near typical offhand spot
    y = offhand != null ? offhand.y - 18 : 44;
        
   	this.addSlot(new BackSlot(new BackContainer(player), 0, x, y, player));
}

    // Helper: find a Slot corresponding to the player's inventory index (0..40)
    private static Slot pokerelics$findPlayerSlotByIndex(AbstractContainerMenu menu, int playerIndex) {
        for (Slot s : menu.slots) {
            if (s.container instanceof Inventory && s.getContainerSlot() == playerIndex) {
                return s;
            }
        }
        return null;
    }

    // Helper: check if our custom slot already exists
    private static int pokerelics$findBackSlotIndex(AbstractContainerMenu menu) {
    	for (int i = 0; i < menu.slots.size(); i++) {
            if (menu.slots.get(i) instanceof BackSlot) return i;
        }
    	return -1;
    }

    // Custom Slot class for the back slot
    private static class BackSlot extends Slot {
        private final Player owner;

        public BackSlot(net.minecraft.world.Container container, int index, int x, int y, Player owner) {
        	super(container, index, x, y);
            this.owner = owner;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            // Accept any item by default; customize here if you want restrictions
            return stack.is(ItemTags.create(ResourceLocation.parse("pokerelics:relics")));
        }

        @Override
        public boolean mayPickup(Player player) {
            // Default behavior, but you can add restrictions if needed
            return super.mayPickup(player);
        }
        

        @Override
        public boolean isActive() {
            // Client-only rendering path will ask this; returning false prevents rendering and hovering.
            // Safe to call owner.isCreative()/isSpectator() here because by the time a screen renders,
            // the client connection/game mode is initialized.
            return !(owner.isCreative() || owner.isSpectator());
        }
        
    }

    // Container that backs the single back slot using PlayerVariables.backSlot
    private static class BackContainer implements net.minecraft.world.Container {
        private final Player player;

        public BackContainer(Player player) {
            this.player = player;
        }

        @Override
        public int getContainerSize() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return getItem(0).isEmpty();
        }

        @Override
        public ItemStack getItem(int index) {
            if (index != 0) return ItemStack.EMPTY;
            PokerelicsModVariables.PlayerVariables vars = player.getData(PokerelicsModVariables.PLAYER_VARIABLES.get());
            if (vars == null) return ItemStack.EMPTY;
            return vars.backSlot.copy();
        }

        @Override
        public ItemStack removeItem(int index, int count) {
            if (index != 0 || count <= 0) return ItemStack.EMPTY;

            PokerelicsModVariables.PlayerVariables vars = player.getData(PokerelicsModVariables.PLAYER_VARIABLES.get());
            if (vars == null) return ItemStack.EMPTY;

            ItemStack current = vars.backSlot.copy();
            if (current.isEmpty()) return ItemStack.EMPTY;

            ItemStack split = current.split(count);
            vars.backSlot = current;
            setChanged();
            return split;
        }

        @Override
        public ItemStack removeItemNoUpdate(int index) {
            if (index != 0) return ItemStack.EMPTY;

           PokerelicsModVariables.PlayerVariables vars = player.getData(PokerelicsModVariables.PLAYER_VARIABLES.get());
            if (vars == null) return ItemStack.EMPTY;

            ItemStack out = vars.backSlot.copy();
            vars.backSlot = ItemStack.EMPTY;
            setChanged();
            return out;
        }

        @Override
        public void setItem(int index, ItemStack stack) {
            if (index != 0) return;
            PokerelicsModVariables.PlayerVariables vars = player.getData(PokerelicsModVariables.PLAYER_VARIABLES.get());
            if (vars == null) return;
            vars.backSlot = stack.copy();
            setChanged();
        }

        @Override
        public void setChanged() {
            // Optional: sync your attachment if needed
        }

        @Override
        public boolean stillValid(Player player) {
            return true;
        }

        @Override
        public void clearContent() {
            setItem(0, ItemStack.EMPTY);
        }
    }
}