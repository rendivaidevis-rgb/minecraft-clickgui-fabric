package com.myclient.client.gui.category;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;

import com.myclient.client.gui.component.ModuleButton;
import com.myclient.client.gui.util.RenderUtils;
import com.myclient.client.module.Module;
import com.myclient.client.module.ModuleRegistry;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CategoryPanel {
    
    private String categoryName;
    private int x, y, width, height;
    private List<ModuleButton> moduleButtons;
    private List<ModuleButton> filteredButtons;
    private boolean expanded = true;
    private int scrollOffset = 0;
    
    private static final int HEADER_HEIGHT = 35;
    private static final int MODULE_BUTTON_HEIGHT = 45;
    private static final int BACKGROUND_COLOR = 0xFF1a1a1a;
    private static final int BORDER_COLOR = 0xFF2a2a2a;
    private static final int HEADER_COLOR = 0xFF242424;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    
    public CategoryPanel(String categoryName, int x, int y, int width, int height) {
        this.categoryName = categoryName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.moduleButtons = new ArrayList<>();
        this.filteredButtons = new ArrayList<>();
        initializeModules();
    }
    
    private void initializeModules() {
        List<Module> modules = ModuleRegistry.getModulesByCategory(categoryName);
        
        for (Module module : modules) {
            ModuleButton button = new ModuleButton(
                module,
                x + 5,
                y + HEADER_HEIGHT + (moduleButtons.size() * MODULE_BUTTON_HEIGHT),
                width - 10,
                MODULE_BUTTON_HEIGHT - 5
            );
            moduleButtons.add(button);
        }
        
        filteredButtons.addAll(moduleButtons);
    }
    
    public void render(DrawContext context, int mouseX, int mouseY, float delta, float openAnimation) {
        float scale = 0.8f + (openAnimation * 0.2f);
        int alpha = (int) (255 * openAnimation);
        
        // Draw background
        int bgColor = (alpha << 24) | (BACKGROUND_COLOR & 0xFFFFFF);
        RenderUtils.drawRoundedRect(context, x, y, width, height, 8, bgColor);
        
        // Draw border
        int borderColor = (alpha << 24) | (BORDER_COLOR & 0xFFFFFF);
        RenderUtils.drawRoundedRectBorder(context, x, y, width, height, 8, 2, borderColor);
        
        // Draw header
        int headerColor = (alpha << 24) | (HEADER_COLOR & 0xFFFFFF);
        context.fill(x, y, x + width, y + HEADER_HEIGHT, headerColor);
        
        // Draw category name
        context.drawTextWithShadow(null, categoryName, x + 10, y + (HEADER_HEIGHT - 8) / 2, TEXT_COLOR);
        
        // Draw expand/collapse arrow
        String arrow = expanded ? "▼" : "▶";
        context.drawTextWithShadow(null, arrow, x + width - 15, y + (HEADER_HEIGHT - 8) / 2, TEXT_COLOR);
        
        // Draw module buttons if expanded
        if (expanded) {
            int currentY = y + HEADER_HEIGHT;
            int visibleHeight = height - HEADER_HEIGHT;
            int maxButtons = (visibleHeight - 10) / MODULE_BUTTON_HEIGHT;
            
            for (int i = scrollOffset; i < Math.min(scrollOffset + maxButtons, filteredButtons.size()); i++) {
                ModuleButton button = filteredButtons.get(i);
                button.setY(currentY + ((i - scrollOffset) * MODULE_BUTTON_HEIGHT));
                button.render(context, mouseX, mouseY, delta);
            }
        }
    }
    
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Check header click for expand/collapse
        if (isMouseOverHeader(mouseX, mouseY)) {
            expanded = !expanded;
            return true;
        }
        
        // Check module buttons
        if (expanded) {
            for (ModuleButton moduleButton : filteredButtons) {
                if (moduleButton.mouseClicked(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void scroll(int amount) {
        int maxScroll = Math.max(0, filteredButtons.size() - ((height - HEADER_HEIGHT - 10) / MODULE_BUTTON_HEIGHT));
        scrollOffset = Math.max(0, Math.min(maxScroll, scrollOffset - amount / 10));
    }
    
    public void filterModules(String query) {
        filteredButtons.clear();
        scrollOffset = 0;
        
        if (query.isEmpty()) {
            filteredButtons.addAll(moduleButtons);
        } else {
            for (ModuleButton button : moduleButtons) {
                if (button.getModule().getName().toLowerCase().contains(query)) {
                    filteredButtons.add(button);
                }
            }
        }
    }
    
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    private boolean isMouseOverHeader(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + HEADER_HEIGHT;
    }
}
