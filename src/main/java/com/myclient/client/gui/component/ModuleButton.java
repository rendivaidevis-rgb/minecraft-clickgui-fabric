package com.myclient.client.gui.component;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;

import com.myclient.client.gui.util.RenderUtils;
import com.myclient.client.module.Module;

@Environment(EnvType.CLIENT)
public class ModuleButton {
    
    private Module module;
    private int x, y, width, height;
    private boolean hovered = false;
    private float toggleAnimation = 0f;
    
    private static final int BACKGROUND_COLOR = 0xFF252525;
    private static final int HOVER_COLOR = 0xFF2f2f2f;
    private static final int ENABLED_COLOR = 0xFF00cc00;
    private static final int DISABLED_COLOR = 0xFFcc0000;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int DESC_TEXT_COLOR = 0xFFaaaaaa;
    private static final float TOGGLE_SPEED = 0.12f;
    
    public ModuleButton(Module module, int x, int y, int width, int height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        hovered = isMouseOver(mouseX, mouseY);
        
        // Update toggle animation
        module.updateAnimation();
        
        // Draw background
        int bgColor = hovered ? HOVER_COLOR : BACKGROUND_COLOR;
        RenderUtils.drawRoundedRect(context, x, y, width, height, 6, bgColor);
        
        // Draw left border accent (indicates enabled state)
        int accentColor = module.isEnabled() ? ENABLED_COLOR : DISABLED_COLOR;
        context.fill(x, y, x + 3, y + height, accentColor);
        
        // Draw module name
        context.drawTextWithShadow(null, module.getName(), x + 12, y + 8, TEXT_COLOR);
        
        // Draw module description
        context.drawTextWithShadow(null, module.getDescription(), x + 12, y + 18, DESC_TEXT_COLOR);
        
        // Draw toggle button (right side)
        int toggleBoxX = x + width - 30;
        int toggleBoxY = y + (height - 12) / 2;
        int toggleBoxSize = 12;
        
        int toggleColor = module.isEnabled() ? 0xFF00cc00 : 0xFF555555;
        RenderUtils.drawRoundedRect(context, toggleBoxX, toggleBoxY, toggleBoxSize, toggleBoxSize, 3, toggleColor);
        
        // Draw toggle state indicator
        if (module.isEnabled()) {
            context.drawTextWithShadow(null, "✓", toggleBoxX + 2, toggleBoxY + 1, TEXT_COLOR);
        }
    }
    
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver((int) mouseX, (int) mouseY) && button == 0) {
            module.toggle();
            return true;
        }
        return false;
    }
    
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    public Module getModule() {
        return module;
    }
    
    public void setY(int newY) {
        this.y = newY;
    }
}
