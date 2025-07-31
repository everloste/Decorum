package dev.bebebea_loste.decorum.client.renderers;

import dev.bebebea_loste.decorum.content.blockEntities.ItemStandBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class ItemStandBlockEntityRenderer implements BlockEntityRenderer<ItemStandBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ItemStandBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ItemStandBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        if (!(entity.getStack().isEmpty())) {
            matrices.push();

            matrices.translate(0.5f, 0.25f+0.03125f+0.03125f, 0.5f);
            if (entity.getWorld() != null) {
                BlockState state = entity.getWorld().getBlockState(entity.getPos());
                if (state.hasBlockEntity()) {
                    Direction facing = state.get(Properties.FACING);
                    switch (facing) {
                        case NORTH:
                            break;
                        case SOUTH:
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                            break;
                        case EAST:
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                            break;
                        case WEST:
                            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                            break;
                    }
                }
            }
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(22.5f));
            matrices.scale(0.6f, 0.6f, 0.6f);

            itemRenderer.renderItem(
                    entity.getStack(),
                    ItemDisplayContext.FIXED,
                    light,
                    overlay,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    1
                    );

            matrices.pop();
        }
    }
}

