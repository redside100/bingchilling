package net.redside.bingchilling.toggleables;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.redside.bingchilling.BingChillingMod;
import net.redside.bingchilling.BingChillingToggleable;

import static net.redside.bingchilling.BingChillingMod.rgbToInt;

public class FreeToggleable extends BingChillingToggleable {

    private boolean wasFlying;
    private double actualX;
    private double actualY;
    private double actualZ;

    public FreeToggleable() {
        super("FREE", "Free", rgbToInt(0, 255, 153));
        this.actualX = 0;
        this.actualY = 0;
        this.actualZ = 0;
        this.wasFlying = false;
    }
    @Override
    public void activate() {
        ClientPlayerEntity player = BingChillingMod.getPlayer();
        this.actualX = player.getX();
        this.actualY = player.getY();
        this.actualZ = player.getZ();
        this.wasFlying = player.getAbilities().flying;
        player.setOnGround(false);
    }

    @Override
    public void deactivate() {
        ClientPlayerEntity player = BingChillingMod.getPlayer();
        player.setVelocity(0, 0, 0);
        player.updatePosition(this.actualX, this.actualY, this.actualZ);
        player.getAbilities().flying = this.wasFlying;
    }

    @Override
    public void tick(MinecraftClient minecraftClient) {
        ClientPlayerEntity player = BingChillingMod.getPlayer();
        if (player == null) {
            this.toggle();
            return;
        }
        player.getAbilities().flying = true;
    }

    @Override
    public void render(InGameHud inGameHud) {

    }

    @Override
    public boolean onPacket(Packet packet) {
        if (packet instanceof PlayerMoveC2SPacket || packet instanceof PlayerInputC2SPacket) {
            return false;
        }
        return true;
    }
}
