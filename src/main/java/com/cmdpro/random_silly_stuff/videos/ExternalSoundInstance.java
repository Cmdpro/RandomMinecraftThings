package com.cmdpro.random_silly_stuff.videos;

import net.minecraft.Util;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.JOrbisAudioStream;
import net.minecraft.client.sounds.LoopingAudioStream;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class ExternalSoundInstance extends SimpleSoundInstance {
    public final Path path;
    public ExternalSoundInstance(Path path, SoundSource pSource, float pVolume, float pPitch, RandomSource pRandom, BlockPos pEntity) {
        super(SoundEvents.MUSIC_MENU.value(), pSource, pVolume, pPitch, pRandom, pEntity);
        this.path = path;
        this.relative = true;
    }

    public ExternalSoundInstance(Path path, SoundSource pSource, float pVolume, float pPitch, RandomSource pRandom, double pX, double pY, double pZ) {
        super(SoundEvents.MUSIC_MENU.value(), pSource, pVolume, pPitch, pRandom, pX, pY, pZ);
        this.path = path;
        this.relative = true;
    }

    @Override
    public CompletableFuture<AudioStream> getStream(SoundBufferLibrary soundBuffers, Sound sound, boolean looping) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                InputStream inputstream = new FileInputStream(new File(path.toUri()));
                return (AudioStream)(looping ? new LoopingAudioStream(JOrbisAudioStream::new, inputstream) : new JOrbisAudioStream(inputstream));
            } catch (IOException ioexception) {
                throw new CompletionException(ioexception);
            }
        }, Util.nonCriticalIoPool());
    }
}
