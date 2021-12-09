package net.redside.bingchilling;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.Packet;

public abstract class BingChillingToggleable {
    private final String command;
    private String name;
    private int color;
    private boolean enabled;
    public BingChillingToggleable(String command, String name, int color) {
        this.command = command;
        this.name = name;
        this.color = color;
        this.enabled = false;
    }

    public String getCommand() {
        return this.command;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            this.activate();
        } else {
            this.deactivate();
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public abstract void activate();
    public abstract void deactivate();
    public abstract void tick(MinecraftClient minecraftClient);
    public abstract void render(InGameHud inGameHud);
    public abstract boolean onPacket(Packet packet);

    public String getName() {
        return this.name;
    }

    public int getColor() {
        return color;
    }
}
