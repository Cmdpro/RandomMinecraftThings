package com.cmdpro.random_silly_stuff.util;

import com.cmdpro.databank.model.DatabankModel;
import com.cmdpro.databank.model.DatabankModels;
import com.cmdpro.databank.model.animation.DatabankAnimationReference;
import com.cmdpro.databank.model.animation.DatabankAnimationState;
import com.cmdpro.databank.model.item.DatabankItemModel;
import com.cmdpro.databank.model.item.DatabankItemRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class BasicAnimatedBlockItemRenderer extends DatabankItemRenderer<BlockItem> {
    public BasicAnimatedBlockItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet, ResourceLocation textureLocation, ResourceLocation modelLocation) {
        super(dispatcher, modelSet, new Model(textureLocation, modelLocation));
    }


    public static class Model extends DatabankItemModel<BlockItem> {
        public DatabankAnimationState animState = new DatabankAnimationState("hand")
                .addAnim(new DatabankAnimationReference("hand", (state, anim) -> {}, (state, anim) -> {}));
        public ResourceLocation textureLocation;
        public ResourceLocation modelLocation;
        public Model(ResourceLocation textureLocation, ResourceLocation modelLocation) {
            this.textureLocation = textureLocation;
            this.modelLocation = modelLocation;
        }
        @Override
        public ResourceLocation getTextureLocation() {
            return textureLocation;
        }

        @Override
        public void setupModelPose(ItemStack stack, float partialTick) {
            animate(animState);
        }

        public DatabankModel model;
        public DatabankModel getModel() {
            if (model == null) {
                model = DatabankModels.models.get(modelLocation);
                animState.updateAnimDefinitions(model);
            }
            return model;
        }
    }
}