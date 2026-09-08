package com.myclient.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ModuleRegistry {
    
    private static final Map<String, List<Module>> modules = new HashMap<>();
    
    static {
        initializeModules();
    }
    
    private static void initializeModules() {
        // Combat modules
        registerModule(new CombatModule("Aura", "Auto-attack nearby entities", "Combat"));
        registerModule(new CombatModule("KillAura", "Automatically attack entities", "Combat"));
        registerModule(new CombatModule("Velocity", "Reduce knockback taken", "Combat"));
        
        // Movement modules
        registerModule(new MovementModule("Speed", "Move faster", "Movement"));
        registerModule(new MovementModule("Flight", "Fly in survival mode", "Movement"));
        registerModule(new MovementModule("Jump", "Double jump ability", "Movement"));
        
        // Render modules
        registerModule(new RenderModule("Tracers", "Draw lines to players", "Render"));
        registerModule(new RenderModule("ESP", "Highlight entities", "Render"));
        registerModule(new RenderModule("FullBright", "Full brightness", "Render"));
        
        // Player modules
        registerModule(new PlayerModule("NoFall", "Prevent fall damage", "Player"));
        registerModule(new PlayerModule("AutoRespawn", "Auto respawn on death", "Player"));
        registerModule(new PlayerModule("Scaffold", "Build blocks under you", "Player"));
        
        // Misc modules
        registerModule(new MiscModule("Spam", "Chat spam", "Misc"));
        registerModule(new MiscModule("AutoHotbar", "Auto item swap", "Misc"));
        registerModule(new MiscModule("ChatFilter", "Filter chat messages", "Misc"));
    }
    
    private static void registerModule(Module module) {
        String category = module.getCategory();
        modules.computeIfAbsent(category, k -> new ArrayList<>()).add(module);
    }
    
    public static List<Module> getModulesByCategory(String category) {
        return modules.getOrDefault(category, new ArrayList<>());
    }
    
    public static List<String> getCategories() {
        return new ArrayList<>(modules.keySet());
    }
    
    public static Module getModuleByName(String name) {
        for (List<Module> list : modules.values()) {
            for (Module module : list) {
                if (module.getName().equalsIgnoreCase(name)) {
                    return module;
                }
            }
        }
        return null;
    }
    
    public static List<Module> getAllModules() {
        List<Module> all = new ArrayList<>();
        modules.values().forEach(all::addAll);
        return all;
    }
    
    /**
     * Dummy module implementations for demonstration
     */
    private static class CombatModule extends Module {
        public CombatModule(String name, String description, String category) {
            super(name, description, category);
        }
    }
    
    private static class MovementModule extends Module {
        public MovementModule(String name, String description, String category) {
            super(name, description, category);
        }
    }
    
    private static class RenderModule extends Module {
        public RenderModule(String name, String description, String category) {
            super(name, description, category);
        }
    }
    
    private static class PlayerModule extends Module {
        public PlayerModule(String name, String description, String category) {
            super(name, description, category);
        }
    }
    
    private static class MiscModule extends Module {
        public MiscModule(String name, String description, String category) {
            super(name, description, category);
        }
    }
}
