package com.cmdpro.random_silly_stuff.registries;

import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.block.RubberDuckBlockEntity;
import com.cmdpro.random_silly_stuff.renderers.RubberDuckRenderer;
import com.cmdpro.random_silly_stuff.block.EmptinessBlockEntity;
import com.cmdpro.random_silly_stuff.client.renderers.block.EmptinessRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(value = Dist.CLIENT, modid = RandomSillyStuff.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, RandomSillyStuff.MODID);
    public static final Supplier<BlockEntityType<EmptinessBlockEntity>> EMPTINESS =
            register("emptiness", () ->
                    BlockEntityType.Builder.of(EmptinessBlockEntity::new,
                            BlockRegistry.EMPTINESS.get()).build(null));
    public static final Supplier<BlockEntityType<RubberDuckBlockEntity>> RUBBER_DUCK =
            register("rubber_duck", () ->
                    BlockEntityType.Builder.of(RubberDuckBlockEntity::new,
                            BlockRegistry.RUBBER_DUCK.get()).build(null));


    private static <T extends BlockEntityType<?>> Supplier<T> register(final String name, final Supplier<T> blockentity) {
        return BLOCK_ENTITIES.register(name, blockentity);
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.RUBBER_DUCK.get(), RubberDuckRenderer::new);
        event.registerBlockEntityRenderer(EMPTINESS.get(), EmptinessRenderer::new);
    }
}
