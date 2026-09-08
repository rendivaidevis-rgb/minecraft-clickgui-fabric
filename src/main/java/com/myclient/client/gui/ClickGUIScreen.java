package com.myclient.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import com.myclient.client.gui.category.CategoryPanel;
import com.myclient.client.gui.util.RenderUtils;
import com.myclient.client.hud.HUDRenderer;
import com.myclient.client.module.ModuleRegistry;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ClickGUIScreen extends Screen {
    
    private List<CategoryPanel> categoryPanels;
    private SearchBar searchBar;
    private HUDRenderer hudRenderer;
    
    private static final int PANEL_WIDTH = 180;
    private static final int PANEL_HEIGHT = 300;
    private static final int PANEL_PADDING = 20;
    private static final int SEARCH_BAR_HEIGHT = 35;
    
    private float openAnimation = 0f;
    private static final float ANIMATION_SPEED = 0.15f;
    
    private boolean isOpening = true;
    private int scrollOffset = 0;
    private static final int SCROLL_SPEED = 15;
    
    public ClickGUIScreen() {
        super(Text.literal("ClickGUI"));
        this.categoryPanels = new ArrayList<>();
        this.hudRenderer = new HUDRenderer();
        initializePanels();
    }
    
    private void initializePanels() {
        String[] categories = {"Combat", "Movement", "Render", "Player", "Misc"};
        int xPos = PANEL_PADDING;
        
        for (String category : categories) {
            CategoryPanel panel = new CategoryPanel(category, xPos, PANEL_PADDING + SEARCH_BAR_HEIGHT + 20, PANEL_WIDTH, PANEL_HEIGHT);
            categoryPanels.add(panel);
            xPos += PANEL_WIDTH + PANEL_PADDING;
        }
        
        this.searchBar = new SearchBar(
            PANEL_PADDING,
            PANEL_PADDING,
            this.width - (PANEL_PADDING * 2),
            SEARCH_BAR_HEIGHT,
            categoryPanels
        );
    }
    
    @Override
    protected void init() {
        super.init();
        if (categoryPanels.isEmpty()) {
            initializePanels();
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Update animation
        if (isOpening && openAnimation < 1f) {
            openAnimation = Math.min(1f, openAnimation + ANIMATION_SPEED);
        } else if (!isOpening && openAnimation > 0f) {
            openAnimation = Math.max(0f, openAnimation - ANIMATION_SPEED);
            if (openAnimation <= 0f) {
                this.close();
                return;
            }
        }
        
        // Draw semi-transparent background
        RenderUtils.drawRect(context, 0, 0, this.width, this.height, 0x00000000);
        
        // Draw background with animation
        int bgColor = (int) (200 * openAnimation) << 24 | 0x1a1a1a;
        context.fill(0, 0, this.width, this.height, bgColor);
        
        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, "MyClient", this.width / 2, 15, 0xFFFFFF);
        
        // Draw search bar
        searchBar.render(context, mouseX, mouseY, delta);
        
        // Draw category panels
        for (CategoryPanel panel : categoryPanels) {
            panel.render(context, mouseX, mouseY, delta, openAnimation);
        }
        
        // Draw HUD (FPS and coordinates)
        hudRenderer.render(context, this.client);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for (CategoryPanel panel : categoryPanels) {
            if (panel.isMouseOver(mouseX, mouseY)) {
                panel.scroll((int) verticalAmount * SCROLL_SPEED);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (searchBar.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        
        for (CategoryPanel panel : categoryPanels) {
            if (panel.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean charTyped(char chr, int modifiers) {
        return searchBar.charTyped(chr, modifiers);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            isOpening = false;
            return true;
        }
        
        if (searchBar.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public void close() {
        super.close();
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
}
