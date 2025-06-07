package com.cmdpro.random_silly_stuff.client;

import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.videos.Video;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderFrameEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

@EventBusSubscriber(modid = RandomSillyStuff.MODID, value = Dist.CLIENT)
public class ClientEvents
{
    public static HashMap<String, Video> videos;
    @SubscribeEvent
    public static void onRenderFrame(RenderFrameEvent.Pre event)
    {
        if (videos == null) {
            videos = new HashMap<>();
            Path path = Minecraft.getInstance().gameDirectory.toPath();
            path = path.resolve("videos");
            if (!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            File videoDirectory = new File(path.toUri());
            File[] files = videoDirectory.listFiles();
            if (files != null) {
                for (File i : files) {
                    if (i.isDirectory()) {
                        videos.put(i.getName(), new Video(i.toPath()));
                    }
                }
            }
        }
    }
}