package com.cmdpro.random_silly_stuff.client.particle;

import com.cmdpro.databank.rendering.RenderHandler;
import com.cmdpro.databank.rendering.RenderTypeHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EmptinessParticle extends TextureSheetParticle {
    public float startQuadSize;
    public Matrix3f matrix;
    protected EmptinessParticle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                                SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.friction = 1.0F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize *= 0.85F;
        startQuadSize = this.quadSize;
        this.lifetime = (int)(10f * (0.5f+(level.getRandom().nextFloat()*0.5f)));
        this.setSpriteFromAge(spriteSet);

        this.rCol = 1f;
        this.gCol = 0.5f + (level.getRandom().nextFloat()*0.5f);
        this.bCol = 1f;
        this.alpha = 0.25f + (level.getRandom().nextFloat()*0.5f);
        this.hasPhysics = false;
        Vec3 target = getPos().add(xd*lifetime, yd*lifetime, zd*lifetime);
        Vec2 vec = getVectorTo(getPos(), target);
        this.matrix = new Matrix3f();
        matrix.rotateY((float)Math.toRadians((double)(-vec.y + 180.0F)+90f));
        matrix.rotateZ((float)Math.toRadians((double)(-vec.x)));
    }

    @Override
    public FacingCameraMode getFacingCameraMode() {
        return (quaternion, camera, partialTick) -> {};
    }

    @Override
    protected void renderRotatedQuad(VertexConsumer buffer, Camera camera, Quaternionf quaternion, float partialTicks) {
        matrix.getNormalizedRotation(quaternion);
        super.renderRotatedQuad(buffer, camera, quaternion, partialTicks);
        super.renderRotatedQuad(buffer, camera, new Quaternionf(quaternion).rotateY((float)Math.toRadians(180)), partialTicks);
        super.renderRotatedQuad(buffer, camera, new Quaternionf(quaternion).rotateX((float)Math.toRadians(90)), partialTicks);
        super.renderRotatedQuad(buffer, camera, new Quaternionf(quaternion).rotateX((float)Math.toRadians(90)).rotateY((float)Math.toRadians(180)), partialTicks);
    }
    private Vec2 getVectorTo(Vec3 from, Vec3 to) {
        double d0 = to.x - from.x;
        double d1 = to.y - from.y;
        double d2 = to.z - from.z;
        return getRotation(new Vec3(d0, d1, d2));
    }
    private Vec2 getRotation(Vec3 vec) {
        double d3 = Math.sqrt(vec.x * vec.x + vec.z * vec.z);
        return new Vec2(Mth.wrapDegrees((float)(-(Mth.atan2(vec.y, d3) * 180.0F / (float)Math.PI))), Mth.wrapDegrees((float)(Mth.atan2(vec.z, vec.x) * 180.0F / (float)Math.PI) - 90.0F));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new EmptinessParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 15728880; // fullbright
    }
}