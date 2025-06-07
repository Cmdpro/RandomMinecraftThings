package com.cmdpro.random_silly_stuff.client.renderers.block;

import com.cmdpro.databank.rendering.RenderHandler;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.block.EmptinessBlockEntity;
import com.cmdpro.random_silly_stuff.client.shaders.RandomSillyStuffCoreShaders;
import com.cmdpro.random_silly_stuff.client.shaders.RandomSillyStuffRenderTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

import java.awt.*;

@EventBusSubscriber(value = Dist.CLIENT, modid = RandomSillyStuff.MODID)
public class EmptinessRenderer implements BlockEntityRenderer<EmptinessBlockEntity> {
    EntityRenderDispatcher renderDispatcher;

    public EmptinessRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        renderDispatcher = rendererProvider.getEntityRenderer();
    }

    public void render(EmptinessBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Matrix4f matrix4f = poseStack.last().pose();
        float shaderTime = time + (Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) / 20f);
        RandomSillyStuffCoreShaders.EMPTINESS.safeGetUniform("Time").set(shaderTime);
        this.renderCube(blockEntity, matrix4f, bufferSource.getBuffer(RandomSillyStuffRenderTypes.EMPTINESS));
    }
    public static float time;
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        time += 1/20f;
    }

    private void renderCube(EmptinessBlockEntity blockEntity, Matrix4f pose, VertexConsumer consumer) {
        float f = this.getOffsetDown();
        float f1 = this.getOffsetUp();
        this.renderFace(blockEntity, pose, consumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0, 1, 1, 0, Direction.SOUTH, new Vec3(1, 1, 1));
        this.renderFace(blockEntity, pose, consumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1, 0, 0, 1, Direction.NORTH, new Vec3(1, 1, 1));
        this.renderFace(blockEntity, pose, consumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1, 0, 0, 1, Direction.EAST, new Vec3(1, 1, 1));
        this.renderFace(blockEntity, pose, consumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0, 1, 1, 0, Direction.WEST, new Vec3(1, 1, 1));
        this.renderFace(blockEntity, pose, consumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, 0, 0, 1, 1, Direction.DOWN, new Vec3(1, 1, 1));
        this.renderFace(blockEntity, pose, consumer, 0.0F, 1.0F, f1, f1, 1.0F, 1.0F, 0.0F, 0.0F, 0, 0, 1, 1, Direction.UP, new Vec3(1, 1, 1));
    }

    private void renderFace(
            EmptinessBlockEntity blockEntity,
            Matrix4f pose,
            VertexConsumer consumer,
            float x0,
            float x1,
            float y0,
            float y1,
            float z0,
            float z1,
            float z2,
            float z3,
            float minUvX,
            float minUvY,
            float maxUvX,
            float maxUvY,
            Direction direction,
            Vec3 normal
    ) {
        if (Block.shouldRenderFace(blockEntity.getBlockState(), blockEntity.getLevel(), blockEntity.getBlockPos(), direction, blockEntity.getBlockPos().relative(direction))) {
            int color = new Color(0xFFe100ff).getRGB();
            float normalX = (float)normal.x;
            float normalY = (float)normal.y;
            float normalZ = (float)normal.z;
            consumer.addVertex(pose, x0, y0, z0).setUv(minUvX, minUvY).setColor(color).setNormal(normalX, normalY, normalZ);
            consumer.addVertex(pose, x1, y0, z1).setUv(maxUvX, minUvY).setColor(color).setNormal(normalX, normalY, normalZ);
            consumer.addVertex(pose, x1, y1, z2).setUv(maxUvX, maxUvY).setColor(color).setNormal(normalX, normalY, normalZ);
            consumer.addVertex(pose, x0, y1, z3).setUv(minUvX, maxUvY).setColor(color).setNormal(normalX, normalY, normalZ);
        }
    }

    protected float getOffsetUp() {
        return 1f;
    }

    protected float getOffsetDown() {
        return 0f;
    }
}
