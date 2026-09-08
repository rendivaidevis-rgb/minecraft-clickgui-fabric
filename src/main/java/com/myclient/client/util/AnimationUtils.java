package com.myclient.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class AnimationUtils {
    
    /**
     * Linear interpolation between two values
     */
    public static float lerp(float start, float end, float progress) {
        return start + (end - start) * progress;
    }
    
    /**
     * Ease-out cubic animation (smooth deceleration)
     */
    public static float easeOutCubic(float progress) {
        float p = progress - 1f;
        return p * p * p + 1f;
    }
    
    /**
     * Ease-in cubic animation (smooth acceleration)
     */
    public static float easeInCubic(float progress) {
        return progress * progress * progress;
    }
    
    /**
     * Ease-in-out cubic animation (smooth start and end)
     */
    public static float easeInOutCubic(float progress) {
        if (progress < 0.5f) {
            return 4f * progress * progress * progress;
        } else {
            float p = 2f * progress - 2f;
            return 0.5f * p * p * p + 1f;
        }
    }
    
    /**
     * Ease-out quad animation
     */
    public static float easeOutQuad(float progress) {
        return 1f - (1f - progress) * (1f - progress);
    }
    
    /**
     * Ease-in quad animation
     */
    public static float easeInQuad(float progress) {
        return progress * progress;
    }
    
    /**
     * Smooth step animation (Smoothstep function)
     */
    public static float smoothstep(float progress) {
        return progress * progress * (3f - 2f * progress);
    }
}
