package com.cmdpro.random_silly_stuff.registries;

import com.cmdpro.databank.Databank;
import com.cmdpro.databank.worldgui.WorldGuiType;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.videos.VideoWorldGuiType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class WorldGuiRegistry {
    public static final DeferredRegister<WorldGuiType> WORLD_GUI_TYPES = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(Databank.MOD_ID, "world_gui_types"),
            RandomSillyStuff.MODID);
    public static final Supplier<WorldGuiType> VIDEO = register("video", VideoWorldGuiType::new);
    private static <T extends WorldGuiType> Supplier<T> register(final String name, final Supplier<T> gui) {
        return WORLD_GUI_TYPES.register(name, gui);
    }
}
