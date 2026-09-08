package com.myclient.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import com.myclient.client.gui.category.CategoryPanel;
import com.myclient.client.gui.util.RenderUtils;

import java.util.List;

@Environment(EnvType.CLIENT)
public class SearchBar {
    
    private int x, y, width, height;
    private String searchText = "";
    private boolean focused = false;
    private List<CategoryPanel> panels;
    
    private static final int BACKGROUND_COLOR = 0xFF2a2a2a;
    private static final int BORDER_COLOR = 0xFF404040;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int PLACEHOLDER_COLOR = 0xFF808080;
    
    public SearchBar(int x, int y, int width, int height, List<CategoryPanel> panels) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.panels = panels;
    }
    
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        boolean mouseOver = isMouseOver(mouseX, mouseY);
        
        // Draw background
        RenderUtils.drawRoundedRect(context, x, y, width, height, 8, BACKGROUND_COLOR);
        
        // Draw border
        int borderColor = focused || mouseOver ? 0xFF7a7a7a : BORDER_COLOR;
        RenderUtils.drawRoundedRectBorder(context, x, y, width, height, 8, 2, borderColor);
        
        // Draw search icon
        context.drawTextWithShadow(null, "🔍", x + 10, y + (height - 8) / 2, TEXT_COLOR);
        
        // Draw text
        String displayText = searchText.isEmpty() && !focused ? "Search modules..." : searchText;
        int textColor = searchText.isEmpty() && !focused ? PLACEHOLDER_COLOR : TEXT_COLOR;
        context.drawTextWithShadow(null, displayText, x + 30, y + (height - 8) / 2, textColor);
    }
    
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver((int) mouseX, (int) mouseY)) {
            this.focused = true;
            return true;
        } else {
            this.focused = false;
        }
        return false;
    }
    
    public boolean charTyped(char chr, int modifiers) {
        if (!focused) return false;
        
        if (chr >= 32 && chr < 127) {
            searchText += chr;
            applySearch();
            return true;
        }
        return false;
    }
    
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!focused) return false;
        
        if (keyCode == 259) { // BACKSPACE
            if (!searchText.isEmpty()) {
                searchText = searchText.substring(0, searchText.length() - 1);
                applySearch();
            }
            return true;
        }
        return false;
    }
    
    private void applySearch() {
        String query = searchText.toLowerCase();
        for (CategoryPanel panel : panels) {
            panel.filterModules(query);
        }
    }
    
    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
