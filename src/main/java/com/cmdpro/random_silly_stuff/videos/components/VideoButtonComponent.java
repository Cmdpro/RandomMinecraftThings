package com.cmdpro.random_silly_stuff.videos.components;

import com.cmdpro.databank.worldgui.WorldGui;
import com.cmdpro.databank.worldgui.components.WorldGuiComponentType;
import com.cmdpro.databank.worldgui.components.types.WorldGuiButtonComponent;
import com.cmdpro.random_silly_stuff.registries.WorldGuiComponentRegistry;
import com.cmdpro.random_silly_stuff.videos.VideoWorldGui;
import com.cmdpro.random_silly_stuff.videos.VideoWorldGuiType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class VideoButtonComponent extends WorldGuiButtonComponent {
    public VideoButtonComponent(WorldGui gui, int x, int y, int width, int height) {
        super(gui, x, y, width, height);
    }

    @Override
    public void renderNormal(GuiGraphics guiGraphics) {
        int buttonColor = 0xFFaaaaaa;
        int buttonOutlineColor = 0xFFaaaaaa;
        int outlinePixels = 2;
        int textOffset = -4;
        guiGraphics.fill(x, y, x+width, y+height, buttonOutlineColor);
        guiGraphics.fill(x+outlinePixels, y+outlinePixels, x+width-outlinePixels, y+height-outlinePixels, buttonColor);
        guiGraphics.drawCenteredString(ClientHandler.getFont(), Component.literal("Press me!"), x+(width/2), y+(height/2)+textOffset, 0xFFFFFFFF);
    }

    @Override
    public void renderHovered(GuiGraphics guiGraphics) {
        int buttonColor = 0xFFaaaaaa;
        int buttonOutlineColor = 0xFFFFFFFF;
        int outlinePixels = 2;
        int textOffset = -4;
        guiGraphics.fill(x, y, x+width, y+height, buttonOutlineColor);
        guiGraphics.fill(x+outlinePixels, y+outlinePixels, x+width-outlinePixels, y+height-outlinePixels, buttonColor);
        guiGraphics.drawCenteredString(ClientHandler.getFont(), Component.literal("Press me!"), x+(width/2), y+(height/2)+textOffset, 0xFFFFFFFF);
    }

    @Override
    public void leftClickButton(boolean b, Player player, int i, int i1) {
        if (gui instanceof VideoWorldGui gui) {
            gui.removeComponent(this);
            gui.active = true;
            sync();
        }
    }

    @Override
    public void rightClickButton(boolean b, Player player, int i, int i1) {

    }

    @Override
    public WorldGuiComponentType getType() {
        return WorldGuiComponentRegistry.VIDEO_BUTTON.get();
    }

    private static class ClientHandler {
        public static Font getFont() {
            return Minecraft.getInstance().font;
        }
    }
}
