package net.redside.bingchilling.toggleables;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.Packet;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.redside.bingchilling.BingChillingMod;
import net.redside.bingchilling.BingChillingToggleable;

import static net.redside.bingchilling.BingChillingMod.rgbToInt;

public class LocationToggleable extends BingChillingToggleable {

    public LocationToggleable() {
        super("LOCATION", "Location", rgbToInt(204, 102, 255));
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void tick(MinecraftClient minecraftClient) {

    }

    @Override
    public void render(InGameHud inGameHud) {
        MatrixStack matrixStack = new MatrixStack();
        ClientPlayerEntity player = BingChillingMod.getPlayer();
        String locationString = player.getBlockX() + " / " + player.getBlockY() + " / " + player.getBlockZ();
        int width = MinecraftClient.getInstance().getWindow().getWidth() / 2;
        inGameHud.drawCenteredTextWithShadow(matrixStack, MinecraftClient.getInstance().textRenderer, new LiteralText(locationString).asOrderedText(), width / 2, 10, rgbToInt(204, 102, 255));
    }

    @Override
    public boolean onPacket(Packet packet) {
        return true;
    }
}
