package com.myclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import com.myclient.client.gui.ClickGUIScreen;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class MyClientClient implements ClientModInitializer {
    
    private static KeyBinding openGuiKey;
    private static boolean guiOpen = false;
    
    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.modclient.open_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.modclient.main"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                MinecraftClient mc = MinecraftClient.getInstance();
                if (mc.currentScreen == null || !(mc.currentScreen instanceof ClickGUIScreen)) {
                    mc.setScreen(new ClickGUIScreen());
                } else if (mc.currentScreen instanceof ClickGUIScreen) {
                    mc.setScreen(null);
                }
            }
        });
    }
}
