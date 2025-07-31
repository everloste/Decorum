package dev.bebebea_loste.decorum.content.blockEntities;

import dev.bebebea_loste.decorum.content.blockEntities.templates.SingleStackBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import dev.bebebea_loste.decorum.registries.BlockEntities;

public class ItemStandBlockEntity extends SingleStackBlockEntity {
    public ItemStandBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.ITEM_STAND, pos, state);
    }
}