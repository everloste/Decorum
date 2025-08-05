package dev.bebebea_loste.decorum.content.blocks;

import com.mojang.serialization.MapCodec;
import dev.bebebea_loste.decorum.content.blockEntities.CandleHolderBlockEntity;
import dev.bebebea_loste.decorum.content.properties.CandleHolderState;
import dev.bebebea_loste.decorum.registries.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CandleHolder extends BlockWithEntity {
	public CandleHolder(Settings settings) {
		super(settings.nonOpaque());
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CandleHolderBlockEntity(pos, state);
	}

	public static final IntProperty CANDLE_SLOTS = IntProperty.of("candle_slots", 1, 4);
	public static final IntProperty CANDLES = IntProperty.of("candles", 0, 4);
	public static final EnumProperty<CandleHolderState> CANDLE_HOLDER_STATE = EnumProperty.of("state", CandleHolderState.class);
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.LIT);
		builder.add(Properties.FACING); // blockstate-relevant (4)
		builder.add(CANDLE_SLOTS); // blockstate-relevant (4)
		builder.add(CANDLES);
		builder.add(CANDLE_HOLDER_STATE); // blockstate-relevant (3)
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return (sideCoversSmallSquare(world, pos.down(), Direction.UP) || sideCoversSmallSquare(world, pos.up(), Direction.DOWN));
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	private static final VoxelShape SHAPE = Block.createColumnShape(4.0F, 0.0F, 6.0F);
	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		world.setBlockState(pos, state.with(Properties.LIT, false));
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack playerStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		CandleHolderBlockEntity blockEntity = (CandleHolderBlockEntity) world.getBlockEntity(pos);
		if (blockEntity instanceof CandleHolderBlockEntity) {
			if (playerStack.isIn(ItemTags.CANDLES) && state.get(CANDLES) < 4 && state.get(CANDLE_SLOTS) >= state.get(CANDLES) + 1) {
				blockEntity.addStack(playerStack.copyWithCount(1));
				playerStack.decrement(1);
				world.setBlockState(pos, state.with(CANDLES, state.get(CANDLES) + 1));

				world.playSound(
						player,
						pos,
						SoundEvents.BLOCK_LANTERN_PLACE,
						SoundCategory.BLOCKS,
						0.25f,
						1
				);

				return ActionResult.SUCCESS;
			}
			else if (playerStack.isOf(Blocks.CANDLE_HOLDER.asItem()) && state.get(CANDLE_SLOTS) < 4) {
				world.setBlockState(pos, state.with(CANDLE_SLOTS, state.get(CANDLE_SLOTS) + 1));
				playerStack.decrement(1);

				world.playSound(
						player,
						pos,
						SoundEvents.BLOCK_LANTERN_PLACE,
						SoundCategory.BLOCKS,
						0.25f,
						1
				);

				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}
}
