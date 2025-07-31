package dev.bebebea_loste.decorum.content.blocks;

import com.mojang.serialization.MapCodec;
import dev.bebebea_loste.decorum.content.blockEntities.ItemStandBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
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

public class ItemStand extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createColumnShape(6.0F, 0.0F, 6.0F);

    public ItemStand(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemStandBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.FACING);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer != null) {
            world.setBlockState(pos, state.with(Properties.FACING, placer.getHorizontalFacing().getOpposite()));
        }
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack playerStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStandBlockEntity entity = (ItemStandBlockEntity) world.getBlockEntity(pos);

        if (entity instanceof ItemStandBlockEntity) {
            if (playerStack.isEmpty() && !entity.getStack().isEmpty()) {
                player.giveItemStack(entity.getStack().copyAndEmpty());
                entity.setStack(ItemStack.EMPTY);
                world.playSound(
                        player,
                        pos,
                        SoundEvents.BLOCK_LANTERN_STEP,
                        SoundCategory.BLOCKS
                );
                return ActionResult.SUCCESS;
            }
            else if (entity.getStack().isEmpty() && !playerStack.isEmpty()) {
                ItemStack newStack = playerStack.copy();
                newStack.setCount(1);
                entity.setStack(newStack);
                playerStack.decrement(1);
                world.playSound(
                        player,
                        pos,
                        SoundEvents.BLOCK_LANTERN_STEP,
                        SoundCategory.BLOCKS
                );
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
