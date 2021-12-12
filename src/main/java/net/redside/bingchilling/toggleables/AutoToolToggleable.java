package net.redside.bingchilling.toggleables;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.BlockHitResult;
import net.redside.bingchilling.BingChillingToggleable;

import static net.redside.bingchilling.BingChillingMod.getPlayer;
import static net.redside.bingchilling.BingChillingMod.rgbToInt;

public class AutoToolToggleable extends BingChillingToggleable{


    public AutoToolToggleable() {
        super("AUTOTOOL", "AutoTool", rgbToInt(102, 153, 254));
    }

    @Override
    public void activate(String fullCommand) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void tick(MinecraftClient minecraftClient) {
        ClientPlayerEntity player = getPlayer();
        if (player == null) {
            setEnabled(false);
            deactivate();
            return;
        }
        if (minecraftClient.options.keyAttack.isPressed()) {
            if (!(minecraftClient.crosshairTarget instanceof BlockHitResult)) {
                return;
            }
            BlockHitResult result = (BlockHitResult) minecraftClient.crosshairTarget;
            BlockState blockState = player.world.getBlockState(result.getBlockPos());
            int mostEfficientTool = -1;
            float highestSpeedMultiplier = 1;
            for (int i = 0; i < 9; i++) {
                ItemStack hotbarItem = player.getInventory().getStack(i);
                float speedMultiplier = hotbarItem.getMiningSpeedMultiplier(blockState);
                if (speedMultiplier > highestSpeedMultiplier) {
                    mostEfficientTool = i;
                    highestSpeedMultiplier = speedMultiplier;
                }
            }
            if (mostEfficientTool != -1 && getPlayer().getInventory().selectedSlot != mostEfficientTool) {
                getPlayer().getInventory().selectedSlot = mostEfficientTool;
            }
        }
    }

    @Override
    public void render(InGameHud inGameHud) {

    }

    @Override
    public boolean onPacket(Packet packet) {
        return true;
    }
}
