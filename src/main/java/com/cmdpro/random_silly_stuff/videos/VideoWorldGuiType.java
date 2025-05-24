package com.cmdpro.random_silly_stuff.videos;

import com.cmdpro.databank.worldgui.WorldGui;
import com.cmdpro.databank.worldgui.WorldGuiEntity;
import com.cmdpro.databank.worldgui.WorldGuiType;
import com.cmdpro.random_silly_stuff.RandomSillyStuff;
import net.minecraft.world.phys.Vec2;

public class VideoWorldGuiType extends WorldGuiType {
    @Override
    public WorldGui createGui(WorldGuiEntity worldGuiEntity) {
        return new VideoWorldGui(worldGuiEntity);
    }

    @Override
    public Vec2 getMenuWorldSize(WorldGuiEntity worldGuiEntity) {
        return new Vec2(3f, 3f);
    }

    @Override
    public Vec2 getRenderSize() {
        return new Vec2(400, 400);
    }
}
