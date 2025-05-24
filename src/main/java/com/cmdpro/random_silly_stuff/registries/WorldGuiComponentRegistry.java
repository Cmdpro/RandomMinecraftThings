package com.cmdpro.random_silly_stuff.registries;

import com.cmdpro.databank.Databank;
import com.cmdpro.databank.worldgui.components.WorldGuiComponentType;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.videos.components.VideoButtonComponentType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class WorldGuiComponentRegistry {
    public static final DeferredRegister<WorldGuiComponentType> WORLD_GUI_COMPONENTS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(Databank.MOD_ID, "world_gui_components"),
            RandomSillyStuff.MODID);
    public static final Supplier<WorldGuiComponentType> VIDEO_BUTTON = register("video_button", VideoButtonComponentType::new);
    private static <T extends WorldGuiComponentType> Supplier<T> register(final String name, final Supplier<T> gui) {
        return WORLD_GUI_COMPONENTS.register(name, gui);
    }
}
