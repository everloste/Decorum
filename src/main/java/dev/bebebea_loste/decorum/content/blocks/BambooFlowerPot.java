package dev.bebebea_loste.decorum.content.blocks;

import com.mojang.serialization.MapCodec;
import dev.bebebea_loste.decorum.content.blockEntities.FlowerPotBlockEntity;
import dev.bebebea_loste.decorum.content.properties.FlowerPotState;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BambooFlowerPot extends BlockWithEntity {
	private static final VoxelShape SHAPE = Block.createColumnShape(6.0F, 0.0F, 6.0F);

	public static final EnumProperty<FlowerPotState> FLOWER_POT_TYPE = EnumProperty.of("type", FlowerPotState.class);

	public BambooFlowerPot(Settings settings) {
		super(settings.sounds(BlockSoundGroup.BAMBOO_WOOD).nonOpaque());
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FlowerPotBlockEntity(pos, state);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FLOWER_POT_TYPE);
	}

	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(FLOWER_POT_TYPE) == FlowerPotState.MEDIUM) {
			return Block.createColumnShape(8f, 0f, 5f);
		}
		else if (state.get(FLOWER_POT_TYPE) == FlowerPotState.BIG) {
			return Block.createColumnShape(10f, 0f, 5f);
		}
		return SHAPE;
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getOutlineShape(state, world, pos, context);
	}

	protected ActionResult onUseWithItem(ItemStack playerStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		FlowerPotBlockEntity blockEntity = (FlowerPotBlockEntity) world.getBlockEntity(pos);
		if (blockEntity instanceof FlowerPotBlockEntity) {
			ItemStack blockStack = blockEntity.getStack();

			if (blockStack.isEmpty() && (playerStack.isIn(ItemTags.SMALL_FLOWERS) || playerStack.isIn(ItemTags.SAPLINGS) || playerStack.isOf(Items.FERN))) {
				// Set item
				ItemStack newStack = playerStack.copy();
				newStack.setCount(1);
				blockEntity.setStack(newStack);
				playerStack.decrement(1);

				// If item is a sapling, make flower pot wide
				if (newStack.isIn(ItemTags.SAPLINGS)) {
					world.setBlockState(pos, state.with(FLOWER_POT_TYPE, FlowerPotState.BIG));
				}
				else if (newStack.isOf(Items.FERN)) {
					world.setBlockState(pos, state.with(FLOWER_POT_TYPE, FlowerPotState.MEDIUM));
				}

				// Done
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				return ActionResult.SUCCESS;
			}
		}
		if (world.isClient()) {
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

}
