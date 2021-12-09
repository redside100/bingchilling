package net.redside.bingchilling.mixin;

import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.redside.bingchilling.BingChillingMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Future;

@Mixin(ClientConnection.class)
public class BingChillingMixin {
	@Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", cancellable = true)
	private void onPacketSend(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> genericFutureListener, CallbackInfo info) {
		if (BingChillingMod.DEBUG_OUTPUT) {
			if (!(packet instanceof PlayerMoveC2SPacket) && !packet.getClass().getName().startsWith("net.minecraft.network.packet.s2c")) {
				System.out.println("Sent " + packet.getClass().getName());
			}
		}
		if (!BingChillingMod.onPacket(packet)) {
			info.cancel();
		}
	}
}
