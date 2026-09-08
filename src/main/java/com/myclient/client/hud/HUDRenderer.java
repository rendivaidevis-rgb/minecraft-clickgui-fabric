package com.myclient.client.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;

import com.myclient.client.gui.util.RenderUtils;

@Environment(EnvType.CLIENT)
public class HUDRenderer {
    
    private static final int BACKGROUND_COLOR = 0xCC1a1a1a;
    private static final int BORDER_COLOR = 0xFF2a2a2a;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int HUD_PADDING = 10;
    private static final int HUD_WIDTH = 120;
    private static final int HUD_HEIGHT = 70;
    
    public void render(DrawContext context, MinecraftClient client) {
        if (client.player == null) return;
        
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        // Position HUD in bottom-right corner
        int hudX = screenWidth - HUD_WIDTH - HUD_PADDING;
        int hudY = screenHeight - HUD_HEIGHT - HUD_PADDING;
        
        // Draw background
        RenderUtils.drawRoundedRect(context, hudX, hudY, HUD_WIDTH, HUD_HEIGHT, 6, BACKGROUND_COLOR);
        
        // Draw border
        RenderUtils.drawRoundedRectBorder(context, hudX, hudY, HUD_WIDTH, HUD_HEIGHT, 6, 1, BORDER_COLOR);
        
        // Draw FPS
        int fps = MinecraftClient.getInstance().getCurrentFps();
        String fpsText = "FPS: " + fps;
        context.drawTextWithShadow(null, fpsText, hudX + 8, hudY + 8, getFPSColor(fps));
        
        // Draw coordinates
        double x = Math.round(client.player.getX() * 10.0) / 10.0;
        double y = Math.round(client.player.getY() * 10.0) / 10.0;
        double z = Math.round(client.player.getZ() * 10.0) / 10.0;
        
        String coordText = "XYZ";
        context.drawTextWithShadow(null, coordText, hudX + 8, hudY + 20, TEXT_COLOR);
        
        String xText = "X: " + x;
        context.drawTextWithShadow(null, xText, hudX + 8, hudY + 30, 0xFFFF5555);
        
        String yText = "Y: " + y;
        context.drawTextWithShadow(null, yText, hudX + 8, hudY + 40, 0xFF55FF55);
        
        String zText = "Z: " + z;
        context.drawTextWithShadow(null, zText, hudX + 8, hudY + 50, 0xFF5555FF);
    }
    
    private int getFPSColor(int fps) {
        if (fps >= 60) {
            return 0xFF55FF55; // Green
        } else if (fps >= 30) {
            return 0xFFFFFF55; // Yellow
        } else {
            return 0xFFFF5555; // Red
        }
    }
}
