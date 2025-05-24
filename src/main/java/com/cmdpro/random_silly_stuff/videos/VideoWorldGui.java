package com.cmdpro.random_silly_stuff.videos;

import com.cmdpro.databank.worldgui.WorldGui;
import com.cmdpro.databank.worldgui.WorldGuiEntity;
import com.cmdpro.databank.worldgui.WorldGuiType;
import com.cmdpro.random_silly_stuff.ClientEvents;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import com.cmdpro.random_silly_stuff.registries.SoundRegistry;
import com.cmdpro.random_silly_stuff.registries.WorldGuiRegistry;
import com.cmdpro.random_silly_stuff.videos.components.VideoButtonComponent;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoWorldGui extends WorldGui {
    public boolean active = false;
    public int time = 0;
    public String video;
    //public List<List<FormattedCharSequence>> text;
    public VideoButtonComponent testButtonComponent;

    public VideoWorldGui(WorldGuiEntity entity) {
        super(entity);
        /*List<String> text = ClientHandler.getText();
        this.text = new ArrayList<>();
        for (String i : text) {
            List<FormattedCharSequence> lines = ClientHandler.getFont().split(Component.literal(i), 480);
            this.text.add(lines);
        }*/
    }

    @Override
    public void addInitialComponents() {
        int buttonCenterX = 400/2;
        int buttonCenterY = 400/2;
        int buttonWidth = 60;
        int buttonHeight = 30;
        testButtonComponent = new VideoButtonComponent(this, buttonCenterX-(buttonWidth/2), buttonCenterY-(buttonHeight/2), buttonWidth, buttonHeight);
        addComponent(testButtonComponent);
    }

    @Override
    public WorldGuiType getType() {
        return WorldGuiRegistry.VIDEO.get();
    }

    @Override
    public List<Matrix3f> getMatrixs() {
        List<Matrix3f> matrixs = new ArrayList<>();
        addMatrixsForFacingPlayer(matrixs, true, false);
        return matrixs;
    }

    @Override
    public void sendData(CompoundTag compoundTag) {
        compoundTag.putBoolean("active", active);
        if (video != null) {
            compoundTag.putString("video", video);
        }
    }

    @Override
    public void recieveData(CompoundTag compoundTag) {
        active = compoundTag.getBoolean("active");
        if (compoundTag.contains("video")) {
            video = compoundTag.getString("video");
        }
    }
    public boolean hasStartedAudio = false;
    @Override
    public void renderGui(GuiGraphics guiGraphics) {
        if (!active) {
            guiGraphics.fill(0, 0, 400, 400, 0xFF000000);
        } else {
            guiGraphics.fill(0, 0, 400, 400, 0xFF000000);
            if (ClientHandler.getVideo(video) != null) {
                if (!hasStartedAudio) {
                    ClientHandler.startSound(video);
                    hasStartedAudio = true;
                }
                int frame = (int) Math.floor((float) time / 2f);
                Vec2 videoSize = new Vec2(ClientHandler.getVideo(video).width, ClientHandler.getVideo(video).height);
                if (videoSize.x > videoSize.y) {
                    videoSize = new Vec2(1f, videoSize.y/videoSize.x);
                } else {
                    videoSize = new Vec2(videoSize.x/videoSize.y, 1f);
                }
                videoSize = videoSize.scale(400);
                int x = (400 - (int) videoSize.x) / 2;
                int y = (400 - (int) videoSize.y) / 2;
                int width = (int) videoSize.x;
                int height = (int) videoSize.y;
                ClientHandler.renderVideo(this, frame, guiGraphics, x, x + width, y, y + height);
            }
        }
        renderComponents(guiGraphics);
        lastFrameActive = active;
    }
    private boolean lastFrameActive = false;
    @Override
    public void tick() {
        if (active) {
            time++;
        }
    }
    private static class ClientHandler {
        public static Font getFont() {
            return Minecraft.getInstance().font;
        }
        public static Video getVideo(String video) {
            return ClientEvents.videos.getOrDefault(video, null);
        }
        public static void startSound(String videoId) {
            Video video = getVideo(videoId);
            if (video != null) {
                Minecraft.getInstance().getSoundManager().play(video.soundInstance);
            }
        }
        public static void renderVideo(VideoWorldGui gui, int frame, GuiGraphics graphics, int x1, int x2, int y1, int y2) {
            Video video = getVideo(gui.video);
            if (video != null) {
                if (video.frames.size() > frame) {
                    video.setFrame(frame);
                    RenderSystem.setShaderTexture(0, video.textureId);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    Matrix4f matrix4f = graphics.pose().last().pose();
                    BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    bufferbuilder.addVertex(matrix4f, (float) x1, (float) y1, 0f).setUv(0, 0);
                    bufferbuilder.addVertex(matrix4f, (float) x1, (float) y2, 0f).setUv(0, 1);
                    bufferbuilder.addVertex(matrix4f, (float) x2, (float) y2, 0f).setUv(1, 1);
                    bufferbuilder.addVertex(matrix4f, (float) x2, (float) y1, 0f).setUv(1, 0);
                    BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
                }
            }
        }
    }
}
