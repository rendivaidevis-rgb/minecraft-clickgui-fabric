package com.myclient.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public abstract class Module {
    
    protected String name;
    protected String description;
    protected String category;
    protected boolean enabled = false;
    protected float animationProgress = 0f;
    private static final float ANIMATION_SPEED = 0.1f;
    
    public Module(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    /**
     * Called when the module is enabled
     */
    public void onEnable() {}
    
    /**
     * Called when the module is disabled
     */
    public void onDisable() {}
    
    /**
     * Called every client tick
     */
    public void onTick() {}
    
    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }
    
    public void enable() {
        if (!enabled) {
            enabled = true;
            onEnable();
        }
    }
    
    public void disable() {
        if (enabled) {
            enabled = false;
            onDisable();
        }
    }
    
    public void updateAnimation() {
        if (enabled && animationProgress < 1f) {
            animationProgress = Math.min(1f, animationProgress + ANIMATION_SPEED);
        } else if (!enabled && animationProgress > 0f) {
            animationProgress = Math.max(0f, animationProgress - ANIMATION_SPEED);
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public float getAnimationProgress() {
        return animationProgress;
    }
    
    public void setAnimationProgress(float progress) {
        this.animationProgress = progress;
    }
}
