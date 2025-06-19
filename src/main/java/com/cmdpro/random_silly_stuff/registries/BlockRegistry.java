package com.cmdpro.random_silly_stuff.registries;

import com.cmdpro.databank.Databank;
import com.cmdpro.databank.worldgui.WorldGuiType;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.block.RubberDuck;
import com.cmdpro.random_silly_stuff.util.AnimatedBlockItemUtil;
import net.minecraft.core.BlockPos;
import com.cmdpro.random_silly_stuff.block.EmptinessBlock;
import com.cmdpro.random_silly_stuff.videos.VideoWorldGuiType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

@EventBusSubscriber(value = Dist.CLIENT, modid = RandomSillyStuff.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK,
            RandomSillyStuff.MODID);
    public static final DeferredRegister<Item> ITEMS = ItemRegistry.ITEMS;

    public static final Supplier<Block> RUBBER_DUCK = register("rubber_duck",
            () -> new RubberDuck(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).noOcclusion().noCollission().noTerrainParticles()),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));


    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(AnimatedBlockItemUtil.createBasicExtensions(RandomSillyStuff.locate("textures/block/rubber_duck.png"), RandomSillyStuff.locate("rubber_duck")), BlockRegistry.RUBBER_DUCK.get().asItem());
    }

    public static final Supplier<Block> EMPTINESS = registerBlock("emptiness", () -> new EmptinessBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)));
    private static <T extends Block> Supplier<T> registerBlock(final String name, final Supplier<? extends T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> Supplier<T> register(final String name, final Supplier<? extends T> block,
                                                                Function<Supplier<T>, Supplier<? extends Item>> item) {
        Supplier<T> obj = registerBlock(name, block);
        ITEMS.register(name, item.apply(obj));
        return obj;
    }
    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }
    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
}
