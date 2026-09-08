package com.myclient.client.gui.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class RenderUtils {
    
    /**
     * Draws a simple rectangle
     */
    public static void drawRect(DrawContext context, int x, int y, int width, int height, int color) {
        context.fill(x, y, x + width, y + height, color);
    }
    
    /**
     * Draws a rounded rectangle with smooth corners
     */
    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        // Draw main rectangle
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + width, y + height - radius, color);
        
        // Draw corners (simplified circular corners)
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        
        // Top-left corner
        drawCircleQuad(context, x + radius, y + radius, radius, color);
        // Top-right corner
        drawCircleQuad(context, x + width - radius, y + radius, radius, color);
        // Bottom-left corner
        drawCircleQuad(context, x + radius, y + height - radius, radius, color);
        // Bottom-right corner
        drawCircleQuad(context, x + width - radius, y + height - radius, radius, color);
    }
    
    /**
     * Draws a rounded rectangle border
     */
    public static void drawRoundedRectBorder(DrawContext context, int x, int y, int width, int height, int radius, int thickness, int color) {
        // Top border
        context.fill(x + radius, y, x + width - radius, y + thickness, color);
        // Bottom border
        context.fill(x + radius, y + height - thickness, x + width - radius, y + height, color);
        // Left border
        context.fill(x, y + radius, x + thickness, y + height - radius, color);
        // Right border
        context.fill(x + width - thickness, y + radius, x + width, y + height - radius, color);
    }
    
    /**
     * Draws a simple circular quadrant
     */
    private static void drawCircleQuad(DrawContext context, int centerX, int centerY, int radius, int color) {
        for (int i = 0; i < radius; i++) {
            int x = (int) Math.sqrt(radius * radius - i * i);
            context.fill(centerX - x, centerY - i, centerX + x, centerY - i + 1, color);
            context.fill(centerX - x, centerY + i, centerX + x, centerY + i + 1, color);
        }
    }
    
    /**
     * Draws a shadow effect (simple implementation)
     */
    public static void drawShadow(DrawContext context, int x, int y, int width, int height, int blur) {
        int shadowColor = 0x33000000; // Semi-transparent black
        for (int i = blur; i > 0; i--) {
            int alpha = (int) ((shadowColor >> 24) * (1f - (float) i / blur)) & 0xFF;
            int color = (alpha << 24) | (shadowColor & 0xFFFFFF);
            context.fill(x - i, y, x + width + i, y + 1, color);
            context.fill(x - i, y + height - 1, x + width + i, y + height, color);
        }
    }
    
    /**
     * Draws a gradient rectangle
     */
    public static void drawGradient(DrawContext context, int x, int y, int width, int height, int colorStart, int colorEnd) {
        for (int i = 0; i < height; i++) {
            float progress = (float) i / height;
            int color = interpolateColor(colorStart, colorEnd, progress);
            context.fill(x, y + i, x + width, y + i + 1, color);
        }
    }
    
    /**
     * Interpolates between two colors
     */
    public static int interpolateColor(int color1, int color2, float progress) {
        int a1 = (color1 >> 24) & 0xFF;
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        
        int a2 = (color2 >> 24) & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        
        int a = (int) (a1 + (a2 - a1) * progress);
        int r = (int) (r1 + (r2 - r1) * progress);
        int g = (int) (g1 + (g2 - g1) * progress);
        int b = (int) (b1 + (b2 - b1) * progress);
        
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
