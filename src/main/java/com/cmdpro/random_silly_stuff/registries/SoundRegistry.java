package com.cmdpro.random_silly_stuff.registries;

import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, RandomSillyStuff.MODID);
    public static final Supplier<SoundEvent> VIDEO = SOUND_EVENTS.register("video",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(RandomSillyStuff.MODID, "video")));
}
