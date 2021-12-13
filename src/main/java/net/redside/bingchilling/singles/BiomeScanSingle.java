package net.redside.bingchilling.singles;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.Packet;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import net.redside.bingchilling.BingChillingSingle;

import static net.redside.bingchilling.BingChillingMod.getPlayer;
import static net.redside.bingchilling.BingChillingMod.rgbToInt;

public class BiomeScanSingle extends BingChillingSingle {

    private int chunkRadius = 16;
    private final String prefix = "§3BiomeScan §e> §f";
    private final String biomes[] = {
            "none", "taiga", "extreme_hills", "jungle", "mesa", "plains", "savanna", "icy", "the_end", "beach",
            "forest", "ocean", "desert", "river", "swamp", "mushroom", "nether", "underground", "mountain"
    };
    public BiomeScanSingle() {
        super("BIOMESCAN", "BiomeScan", rgbToInt(227, 73, 73));
    }


    @Override
    public void run(String command) {
        ClientPlayerEntity player = getPlayer();
        if (player == null) {
            return;
        }
        if (command.split(" ").length < 2) {
            player.sendMessage(new LiteralText(prefix + "Specify a biome with .biomescan <name>\nValid names are " + String.join(", ", biomes)), false);
            return;
        }

        String biomeMatch = command.split(" ")[1];
        ClientWorld world = MinecraftClient.getInstance().world;
        double closestDistance = Double.MAX_VALUE;
        double closestX = Double.MAX_VALUE;
        double closestZ = Double.MAX_VALUE;
        for (int chunkX = player.getChunkPos().x - chunkRadius - 1; chunkX < player.getChunkPos().x + chunkRadius - 1; chunkX++) {
            for (int chunkZ = player.getChunkPos().z - chunkRadius - 1; chunkZ < player.getChunkPos().z + chunkRadius - 1; chunkZ++) {
                WorldChunk currentChunk = world.getChunk(chunkX, chunkZ);
                if (currentChunk == null || currentChunk.isEmpty()) {
                    continue;
                }
                Biome biome = world.getBiome(new BlockPos(currentChunk.getPos().getCenterX(), 0, currentChunk.getPos().getCenterZ()));
                if (biome.getCategory().getName().equalsIgnoreCase(biomeMatch)) {
                    double distance = Math.sqrt(player.squaredDistanceTo(currentChunk.getPos().getCenterX(), player.getY(), currentChunk.getPos().getCenterZ()));
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestX = currentChunk.getPos().getCenterX();
                        closestZ = currentChunk.getPos().getCenterZ();
                    }
                }
            }
        }
        if (closestX < Double.MAX_VALUE) {
            player.sendMessage(new LiteralText(prefix + "Closest §3" + biomeMatch + " §ffound at §a(" + (int) closestX + ", " + (int) closestZ + ")"), false);
        } else {
            player.sendMessage(new LiteralText(prefix + "No nearby biome found with name §3" + biomeMatch + "§f"), false);
        }
    }
}
