package dev.bebebea_loste.decorum.content.blocks;

import com.mojang.serialization.MapCodec;
import dev.bebebea_loste.decorum.content.blockEntities.TorchHolderBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class TorchHolder extends BlockWithEntity {
	private static final VoxelShape SHAPE = Block.createColumnShape(4.0F, 0.0F, 6.0F);
	private static final VoxelShape SHAPE_WITH_TORCH = Block.createColumnShape(4.0F, 0.0F, 12.0F);
	private static final VoxelShape SHAPE_WITH_CANDLE = Block.createColumnShape(4.0F, 0.0F, 8.0F);
	public static final IntProperty LIT = IntProperty.of("lit", 0, 15);
	public static final BooleanProperty MOUNTED = BooleanProperty.of("mounted");

	public TorchHolder(Settings settings) {
		super(settings.luminance((state) -> state.get(LIT)).sounds(BlockSoundGroup.LANTERN).nonOpaque());
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TorchHolderBlockEntity(pos, state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
		builder.add(Properties.HORIZONTAL_FACING);
		builder.add(MOUNTED);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		// please don't crucify me for this i couldn't find a better method at the time of coding this
		if (world.getBlockState(pos.north()).isSolidBlock(world, pos)) {
			return true;
		}
		else if  (world.getBlockState(pos.south()).isSolidBlock(world, pos)) {
			return true;
		}
		else if (world.getBlockState(pos.west()).isSolidBlock(world, pos)) {
			return true;
		}
		else if (world.getBlockState(pos.east()).isSolidBlock(world, pos)) {
			return true;
		}
		else return sideCoversSmallSquare(world, pos.down(), Direction.UP);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getSide() == Direction.UP) {
			return this.getDefaultState().with(MOUNTED, false).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
		}
		else if (ctx.getSide() == Direction.DOWN) {
			return this.getDefaultState().with(MOUNTED, false);
		}
		else {
			return this.getDefaultState().with(MOUNTED, true).with(Properties.HORIZONTAL_FACING, ctx.getSide());
		}
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
	}

	protected Vec3d getShapeOffset(BlockState state) {
		if (state.get(MOUNTED)) {
			switch (state.get(Properties.HORIZONTAL_FACING)) {
				case NORTH:
					return new Vec3d(0, 4/16f, 5/16f);
				case SOUTH:
					return new Vec3d(0, 4/16f, -5/16f);
				case EAST:
					return new Vec3d(-5/16f, 4/16f, 0);
				case WEST:
					return new Vec3d(5/16f, 4/16f, 0);
			}
		}
		return new Vec3d(0.0, 0.0, 0.0);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		TorchHolderBlockEntity entity = (TorchHolderBlockEntity) world.getBlockEntity(pos);

		if (entity != null) {
			ItemStack stack = entity.getStack();
			if (!stack.isEmpty()) {
				if (stack.isOf(Items.TORCH) || stack.isOf(Items.REDSTONE_TORCH) || stack.isOf(Items.SOUL_TORCH)) {
					return SHAPE_WITH_TORCH.offset(this.getShapeOffset(state));
				}
				else if (stack.isIn(ItemTags.CANDLES)) {
					return SHAPE_WITH_CANDLE.offset(this.getShapeOffset(state));
				}
			}
		}
		return SHAPE.offset(this.getShapeOffset(state));
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE.offset(this.getShapeOffset(state));
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack playerStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		TorchHolderBlockEntity entity = (TorchHolderBlockEntity) world.getBlockEntity(pos);

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
					world.setBlockState(pos, state.with(LIT, 9));
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
						SoundEvents.BLOCK_LANTERN_PLACE,
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
		TorchHolderBlockEntity entity = (TorchHolderBlockEntity) world.getBlockEntity(pos);

		if (entity != null && !entity.getStack().isEmpty()) {
			ItemStack stack = entity.getStack();
			float f = random.nextFloat();

			Vec3d vec3d = new Vec3d(pos.getX() + 0.5d, pos.getY() + 0.8125f, pos.getZ() + 0.5d);
			vec3d = vec3d.add(this.getShapeOffset(state));

			if (stack.isIn(ItemTags.CANDLES)) {
				vec3d = vec3d.add(0d, -0.2295d, 0d);
				world.addParticleClient(ParticleTypes.SMALL_FLAME, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
				if (f < 0.3f) {
					world.addParticleClient(ParticleTypes.SMOKE, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
				}
			}
			else if (stack.isOf(Items.TORCH)) {
				world.addParticleClient(ParticleTypes.FLAME, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
				if (f < 0.5f) {
					world.addParticleClient(ParticleTypes.SMOKE, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
				}
			}
			else if (stack.isOf(Items.SOUL_TORCH)) {
				world.addParticleClient(ParticleTypes.SOUL_FIRE_FLAME, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
				if (f < 0.5f) {
					world.addParticleClient(ParticleTypes.SMOKE, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0F, 0.0F, 0.0F);
				}
			}

			if (f < 0.17f) {
				world.playSoundClient(vec3d.getX(), vec3d.getY(), vec3d.getZ(), SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
			}
		}
	}
}
