package com.cmdpro.random_silly_stuff.client.shaders;

import com.cmdpro.databank.rendering.RenderTypeHandler;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class RandomSillyStuffRenderTypes extends RenderType {
    public RandomSillyStuffRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }
    public static final ShaderStateShard EMPTINESS_SHADER = new ShaderStateShard(RandomSillyStuffCoreShaders::getEmptiness);
    public static final RenderType EMPTINESS = RenderTypeHandler.registerRenderType(create(RandomSillyStuff.MODID + ":emptiness",
            DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            CompositeState.builder()
                    .setShaderState(EMPTINESS_SHADER)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .createCompositeState(false)
    ), false);
}
