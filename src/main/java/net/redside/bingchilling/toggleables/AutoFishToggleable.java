package net.redside.bingchilling.toggleables;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.Hand;
import net.redside.bingchilling.BingChillingToggleable;

import java.lang.reflect.Field;
import java.util.Arrays;

import static net.redside.bingchilling.BingChillingMod.getPlayer;
import static net.redside.bingchilling.BingChillingMod.rgbToInt;

public class AutoFishToggleable extends BingChillingToggleable {
    public AutoFishToggleable() {
        super("AUTOFISH", "AutoFish", rgbToInt(51, 204, 204));
    }
    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void tick(MinecraftClient minecraftClient) {
        ClientPlayerEntity player = getPlayer();
        if (player == null) {
            this.toggle();
            return;
        }
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        ClientWorld world = MinecraftClient.getInstance().world;
        FishingBobberEntity bobberEntity = player.fishHook;
        if (bobberEntity == null) {
            return;
        }

        try {
            Field fields[] = bobberEntity.getClass().getDeclaredFields();
            if (fields.length == 0) {
                return;
            }

            Field caughtFishField = null;
            for (Field field : fields) {
                if (field.getType().equals(boolean.class)) {
                    caughtFishField = field;
                    break;
                }
            }

            if (caughtFishField == null) {
                return;
            }
            caughtFishField.setAccessible(true);
            boolean sunk = caughtFishField.getBoolean(bobberEntity);
            if (sunk) {
                interactionManager.interactItem(player, world, Hand.MAIN_HAND);
                bobberEntity.remove(Entity.RemovalReason.DISCARDED);
                new Thread(() -> {
                    try {
                        Thread.sleep(750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    interactionManager.interactItem(player, world, Hand.MAIN_HAND);
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
