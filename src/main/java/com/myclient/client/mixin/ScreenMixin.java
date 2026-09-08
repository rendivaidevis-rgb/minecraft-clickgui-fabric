package com.myclient.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import com.myclient.client.module.ModuleRegistry;

@Mixin(Screen.class)
public class ScreenMixin {
    
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(CallbackInfo ci) {
        // This can be used for screen-level interactions if needed
    }
}
