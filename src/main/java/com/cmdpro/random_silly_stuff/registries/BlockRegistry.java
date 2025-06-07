package com.cmdpro.random_silly_stuff.registries;

import com.cmdpro.databank.Databank;
import com.cmdpro.databank.worldgui.WorldGuiType;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.block.EmptinessBlock;
import com.cmdpro.random_silly_stuff.videos.VideoWorldGuiType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK,
            RandomSillyStuff.MODID);
    public static final Supplier<Block> EMPTINESS = registerBlock("emptiness", () -> new EmptinessBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)));
    private static <T extends Block> Supplier<T> registerBlock(final String name,
                                                               final Supplier<? extends T> block) {
        return BLOCKS.register(name, block);
    }
}
