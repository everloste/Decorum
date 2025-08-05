package dev.bebebea_loste.decorum.content.blockEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import static dev.bebebea_loste.decorum.registries.BlockEntities.CANDLE_HOLDER;

public class CandleHolderBlockEntity extends BlockEntity implements Inventory {
	protected DefaultedList<ItemStack> inventory;

	public CandleHolderBlockEntity(BlockPos pos, BlockState state) {
		super(CANDLE_HOLDER, pos, state);
		this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
	}

	@Override
	public int size() {
		return this.inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return this.getCandleCount() <= 0;
	}

	public int getCandleCount() {
		return    (this.inventory.get(0).isEmpty() ? 0 : 1)
				+ (this.inventory.get(1).isEmpty() ? 0 : 1)
				+ (this.inventory.get(2).isEmpty() ? 0 : 1)
				+ (this.inventory.get(3).isEmpty() ? 0 : 1);
	}

	@Override
	public ItemStack getStack(int slot) {
		return this.inventory.get(slot);
	}

	public Block getCandleAsBlock(int slot) {
		if (!this.inventory.get(slot).isEmpty() && this.inventory.get(slot).isIn(ItemTags.CANDLES)) {
			return Registries.BLOCK.get(Registries.ITEM.getId(this.getStack(slot).getItem()));
		}
		return null;
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return null;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return null;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		this.inventory.set(slot, stack);
	}

	public void addStack(ItemStack stack) {
		for (int i = 0; i < this.inventory.size(); ++i) {
			if (this.inventory.get(i).isEmpty()) {
				this.setStack(i, stack);
				break;
			}
		}
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return false;
	}

	@Override
	public void clear() {

	}

	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
		super.writeNbt(nbt, registries);
		Inventories.writeNbt(nbt, this.inventory, registries);
	}

	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
		super.readNbt(nbt, registries);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory, registries);
	}
}
