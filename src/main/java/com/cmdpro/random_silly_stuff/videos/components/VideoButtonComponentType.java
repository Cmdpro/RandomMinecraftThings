package com.cmdpro.random_silly_stuff.videos.components;

import com.cmdpro.databank.worldgui.WorldGui;
import com.cmdpro.databank.worldgui.components.WorldGuiComponent;
import com.cmdpro.databank.worldgui.components.types.WorldGuiButtonComponentType;

public class VideoButtonComponentType extends WorldGuiButtonComponentType {

    @Override
    public WorldGuiComponent createComponent(WorldGui worldGui) {
        return new VideoButtonComponent(worldGui, 0, 0, 0, 0);
    }
}
