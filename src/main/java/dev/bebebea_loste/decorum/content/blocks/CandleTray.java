package dev.bebebea_loste.decorum.content.blocks;

import com.mojang.serialization.MapCodec;
import dev.bebebea_loste.decorum.content.blockEntities.CandleTrayBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CandleTray extends BlockWithEntity {
	private static final VoxelShape SHAPE = Block.createColumnShape(4.0F, 0.0F, 6.0F);
	private static final VoxelShape SHAPE_WITH_TORCH = Block.createColumnShape(4.0F, 0.0F, 12.0F);
	private static final VoxelShape SHAPE_WITH_CANDLE = Block.createColumnShape(4.0F, 0.0F, 8.0F);
	public static final IntProperty LIT = IntProperty.of("lit", 0, 15);

	public CandleTray(Settings settings) {
		super(settings.luminance((state) -> state.get(LIT)).sounds(BlockSoundGroup.LANTERN).nonOpaque());
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CandleTrayBlockEntity(pos, state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return sideCoversSmallSquare(world, pos.down(), Direction.UP);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		CandleTrayBlockEntity entity = (CandleTrayBlockEntity) world.getBlockEntity(pos);

		if (entity != null) {
			ItemStack stack = entity.getStack();
			if (!stack.isEmpty()) {
				if (stack.isOf(Items.TORCH)) {
					return SHAPE_WITH_TORCH;
				}
				else if (stack.isOf(Items.REDSTONE_TORCH)) {
					return SHAPE_WITH_TORCH;
				}
				else if (stack.isOf(Items.SOUL_TORCH)) {
					return SHAPE_WITH_TORCH;
				}
				else if (stack.isIn(ItemTags.CANDLES)) {
					return SHAPE_WITH_CANDLE;
				}
			}
		}
		return SHAPE;
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack playerStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		CandleTrayBlockEntity entity = (CandleTrayBlockEntity) world.getBlockEntity(pos);

		if (entity != null && entity.getStack().isEmpty()) {
			if (playerStack.isIn(ItemTags.CANDLES) || playerStack.isOf(Items.TORCH) || playerStack.isOf(Items.REDSTONE_TORCH) || playerStack.isOf(Items.SOUL_TORCH))
			{
				// Set item
				ItemStack newStack = playerStack.copy();
				newStack.setCount(1);
				entity.setStack(newStack);
				playerStack.decrement(1);

				// Set light level
				if (newStack.isOf(Items.TORCH)) {
					world.setBlockState(pos, state.with(LIT, 14));
				}
				else if (newStack.isIn(ItemTags.CANDLES)) {
					world.setBlockState(pos, state.with(LIT, 8));
				}
				else if (newStack.isOf(Items.REDSTONE_TORCH)) {
					world.setBlockState(pos, state.with(LIT, 7));
				}
				else if (newStack.isOf(Items.SOUL_TORCH)) {
					world.setBlockState(pos, state.with(LIT, 10));
				}

				// Finish
				world.playSound(
						player,
						pos,
						SoundEvents.BLOCK_LANTERN_STEP,
						SoundCategory.BLOCKS,
						0.25f,
						1
				);
				return ActionResult.SUCCESS;
			}
		}
		else if (entity != null && !entity.getStack().isEmpty() && playerStack.isEmpty()) {
			ItemStack stack = entity.getStack().copyAndEmpty();
			world.setBlockState(pos, state.with(LIT, 0));
			player.giveItemStack(stack);
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		CandleTrayBlockEntity entity = (CandleTrayBlockEntity) world.getBlockEntity(pos);

		if (entity != null && !entity.getStack().isEmpty()) {
			ItemStack stack = entity.getStack();
			float f = random.nextFloat();

			double px = (double) pos.getX() + (double) 0.5F;
			double py = (double) pos.getY() + 0.8125f;
			double pz = (double) pos.getZ() + (double) 0.5F;

			if (stack.isIn(ItemTags.CANDLES)) {
				py = (double) pos.getY() + 0.583f;
				world.addParticleClient(ParticleTypes.SMALL_FLAME, px, py, pz, (double) 0.0F, (double) 0.0F, (double) 0.0F);
				if (f < 0.3f) {
					world.addParticleClient(ParticleTypes.SMOKE, px, py, pz, (double)0.0F, (double)0.0F, (double)0.0F);
					if (f < 0.17f) {
						world.playSoundClient(px, py, pz, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
					}
				}
			}
			else if (stack.isOf(Items.TORCH)) {
				world.addParticleClient(ParticleTypes.FLAME, px, py, pz, (double) 0.0F, (double) 0.0F, (double) 0.0F);
				if (f < 0.5f) {
					world.addParticleClient(ParticleTypes.SMOKE, px, py, pz, (double) 0.0F, (double) 0.0F, (double) 0.0F);
				}
			}
			else if (stack.isOf(Items.SOUL_TORCH)) {
				world.addParticleClient(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, (double) 0.0F, (double) 0.0F, (double) 0.0F);
				if (f < 0.5f) {
					world.addParticleClient(ParticleTypes.SMOKE, px, py, pz, (double) 0.0F, (double) 0.0F, (double) 0.0F);
				}
			}
		}
	}
}
