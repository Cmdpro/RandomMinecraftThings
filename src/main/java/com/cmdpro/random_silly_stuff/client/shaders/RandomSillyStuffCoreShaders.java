package com.cmdpro.random_silly_stuff.client.shaders;

import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(value = Dist.CLIENT, modid = RandomSillyStuff.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RandomSillyStuffCoreShaders {
    public static ShaderInstance EMPTINESS;
    public static ShaderInstance getEmptiness() {
        return EMPTINESS;
    }
    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath(RandomSillyStuff.MODID, "emptiness"), DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL), shader -> { EMPTINESS = shader; });
    }
}
