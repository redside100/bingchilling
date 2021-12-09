package net.redside.bingchilling.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.redside.bingchilling.BingChillingMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HUDMixin {

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "TAIL"))
    public void onPostHudRender(CallbackInfo ci) {
        BingChillingMod.render((InGameHud) (Object) this);
    }
}