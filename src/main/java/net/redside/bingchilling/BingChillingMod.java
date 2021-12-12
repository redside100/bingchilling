package net.redside.bingchilling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.LiteralText;
import net.redside.bingchilling.toggleables.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;

public class BingChillingMod implements ModInitializer {

	public static boolean DEBUG_OUTPUT = false;
	private static ArrayList<BingChillingToggleable> toggleables = new ArrayList<>();
	private static ArrayList<BingChillingSingle> singles = new ArrayList<>();
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("bingchilling");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello! I like bing chilling!");
		toggleables.add(new LocationToggleable());
		toggleables.add(new BrightToggleable());
		toggleables.add(new FreeToggleable());
		toggleables.add(new AutoFishToggleable());
		toggleables.add(new AutoToolToggleable());
		ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
	}

	public static boolean onPacket(Packet packet) {
		if (packet instanceof ChatMessageC2SPacket chatPacket) {
			if (chatPacket.getChatMessage().startsWith(".")) {
				onCommand(chatPacket.getChatMessage().substring(1).toUpperCase());
				return false;
			}
		}
		boolean send = true;
		for (BingChillingToggleable toggleable : toggleables) {
			if (toggleable.isEnabled()) {
				if (!toggleable.onPacket(packet)) {
					send = false;
				}
			}
		}

		return send;
	}

	private static void onCommand(String command) {
		for (BingChillingToggleable toggleable : toggleables) {
			String commandMatch = command;
			if (command.split(" ").length >= 1) {
				commandMatch = command.split(" ")[0];
			}
			if (toggleable.getCommand().equals(commandMatch)) {
				toggleable.toggle(command);
				String color = toggleable.isEnabled() ? "§a§l" : "§c§l";
				getPlayer().sendMessage(new LiteralText(color + "< " + toggleable.getName() + " >"), true);
				return;
			}
		}
		for (BingChillingSingle single : singles) {
			String commandMatch = command;
			if (command.split(" ").length >= 1) {
				commandMatch = command.split(" ")[0];
			}
			if (single.getCommand().equals(commandMatch)) {
				single.run(command);
				getPlayer().sendMessage(new LiteralText( "§b§l< " + single.getName() + " >"), true);
				return;
			}
		}
		getPlayer().sendMessage(new LiteralText("§c§lNot a valid command!"), true);
	}

	private static void test() {
		getPlayer().sendMessage(new LiteralText("Bing Chilling"), false);
	}

	private static String garbageGenerator(int length) {
		Random random = new Random();
		StringBuilder garbage = new StringBuilder();
		for (int i = 0; i < length; i ++) {
			int charCode = random.nextInt(0x2000, 0x10FFFF);
			garbage.append((char) charCode);
		}
		return garbage.toString();
	}

	public static void render(InGameHud inGameHud) {
		MatrixStack matrixStack = new MatrixStack();

		inGameHud.drawStringWithShadow(matrixStack, MinecraftClient.getInstance().textRenderer, "Bing Chilling", 10, 10, rgbToInt(255, 255, 255));
		int renderIndex = 1;
		for (BingChillingToggleable toggleable : toggleables) {
			if (toggleable.isEnabled()) {
				inGameHud.drawStringWithShadow(
						matrixStack,
						MinecraftClient.getInstance().textRenderer,
						toggleable.getName(),
						10,
						(renderIndex++ * 10) + 10,
						toggleable.getColor()
				);
				toggleable.render(inGameHud);
			}
		}
	}

	private void onTick(MinecraftClient minecraftClient) {
		for (BingChillingToggleable toggleable : toggleables) {
			if (toggleable.isEnabled()) {
				toggleable.tick(minecraftClient);
			}
		}
	}

	public static int rgbToInt(int r, int g, int b) {
		return b + (g << 8) + (r << 16);
	}

	public static ClientPlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}
}
