package com.cmdpro.random_silly_stuff.videos;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Video {
    public final int textureId;
    public final int width;
    public final int height;
    public List<NativeImage> frames = new ArrayList<>();
    public ExternalSoundInstance soundInstance;
    public Video(Path path) {
        soundInstance = new ExternalSoundInstance(path.resolve("audio.ogg"), SoundSource.MASTER, 1f, 1f, RandomSource.create(), 0, 0, 0);
        int width = 0;
        int height = 0;
        try {
            File framesDirectory = new File(path.resolve("frames").toUri());
            File[] videoFiles = framesDirectory.listFiles();
            if (videoFiles != null) {
                for (File i : videoFiles) {
                    if (i.getName().startsWith("frame_") && i.getName().endsWith(".png")) {
                        try {
                            FileInputStream stream = new FileInputStream(i);
                            NativeImage image = NativeImage.read(NativeImage.Format.RGB, stream);
                            width = image.getWidth();
                            height = image.getHeight();
                            frames.add(image);
                            stream.close();
                        } catch (Exception ignored) {}
                    }
                }
            }
        } catch (Exception ignored) {}
        textureId = TextureUtil.generateTextureId();
        this.width = width;
        this.height = height;
    }
    public void free() {
        TextureUtil.releaseTextureId(this.textureId);
        for (NativeImage i : frames) {
            i.close();
        }
    }
    public void setFrame(int frame) {
        NativeImage image = frames.get(frame);
        TextureUtil.prepareImage(textureId, 0, image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, 0, 0, image.getWidth(), image.getHeight(), false, false, false, false);
    }
}