package net.redside.bingchilling.toggleables;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.Packet;
import net.redside.bingchilling.BingChillingMod;
import net.redside.bingchilling.BingChillingToggleable;

public class BrightToggleable extends BingChillingToggleable {

    private double oldValue;

    public BrightToggleable() {
        super("BRIGHT", "Bright", BingChillingMod.rgbToInt(255, 255, 102));
        this.oldValue = 1;
    }
    @Override
    public void activate() {
        this.oldValue = MinecraftClient.getInstance().options.gamma;
        MinecraftClient.getInstance().options.gamma = 999999;
    }

    @Override
    public void deactivate() {
        MinecraftClient.getInstance().options.gamma = this.oldValue;
    }

    @Override
    public void tick(MinecraftClient minecraftClient) {

    }

    @Override
    public void render(InGameHud inGameHud) {

    }

    @Override
    public boolean onPacket(Packet packet) {
        return true;
    }
}
