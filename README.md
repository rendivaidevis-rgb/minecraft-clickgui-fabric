# MyClient ClickGUI - Minecraft 1.21.1 Fabric Mod

A modern, dark-themed PvP-style ClickGUI for Minecraft 1.21.1 using Fabric loader. Features smooth animations, rounded panels, and a modular architecture.

## Features

✨ **Modern Design**
- Dark, clean UI with rounded corners and subtle shadows
- Smooth opening/closing animations
- Responsive hover effects and module toggling

🎮 **Functionality**
- **5 Categories**: Combat, Movement, Render, Player, Misc
- **15+ Modules** with ON/OFF toggles
- Real-time FPS and coordinate display in HUD
- Search bar for quick module filtering
- Scrollable category panels
- Responsive to different screen resolutions

⌨️ **Controls**
- **Right Shift** - Open/Close GUI
- **Mouse Wheel** - Scroll through modules
- **Left Click** - Toggle modules
- **ESC** - Close GUI
- **Search Bar** - Type to filter modules

## Project Structure

```
src/main/
├── java/com/myclient/client/
│   ├── MyClientClient.java              # Main mod entry point
│   ├── gui/
│   │   ├── ClickGUIScreen.java         # Main GUI screen
│   │   ├── SearchBar.java              # Search functionality
│   │   ├── category/
│   │   │   └── CategoryPanel.java      # Category management
│   │   ├── component/
│   │   │   └── ModuleButton.java       # Module UI button
│   │   └── util/
│   │       └── RenderUtils.java        # Rendering utilities
│   ├── module/
│   │   ├── Module.java                 # Base module class
│   │   └── ModuleRegistry.java         # Module registration
│   ├── hud/
│   │   └── HUDRenderer.java            # FPS/Coords HUD
│   ├── util/
│   │   └── AnimationUtils.java         # Animation helpers
│   └── mixin/
│       └── ScreenMixin.java            # Mixin injection
├── resources/
│   ├── fabric.mod.json                 # Mod metadata
│   ├── modclient.mixins.json           # Mixin configuration
│   └── modclient.accesswidener         # Access widener
```

## Building & Installation

### Requirements
- Java 21 or higher
- Gradle 8.0+
- Minecraft 1.21.1
- Fabric Loader 0.16.5+

### Build Instructions

```bash
# Clone the repository
git clone https://github.com/rendivaidevis-rgb/minecraft-clickgui-fabric.git
cd minecraft-clickgui-fabric

# Build the mod
./gradlew build

# The compiled JAR will be at: build/libs/modclient-1.0.0.jar
```

### Installation

1. Locate your Minecraft mods folder (`~/.minecraft/mods` or `%APPDATA%\.minecraft\mods`)
2. Place `modclient-1.0.0.jar` into the mods folder
3. Launch Minecraft with Fabric profile
4. Press **Right Shift** to open the ClickGUI

## Module Categories

### Combat
- **Aura** - Auto-attack nearby entities
- **KillAura** - Automatically attack entities
- **Velocity** - Reduce knockback taken

### Movement
- **Speed** - Move faster
- **Flight** - Fly in survival mode
- **Jump** - Double jump ability

### Render
- **Tracers** - Draw lines to players
- **ESP** - Highlight entities
- **FullBright** - Full brightness

### Player
- **NoFall** - Prevent fall damage
- **AutoRespawn** - Auto respawn on death
- **Scaffold** - Build blocks under you

### Misc
- **Spam** - Chat spam
- **AutoHotbar** - Auto item swap
- **ChatFilter** - Filter chat messages

## Customization

### Adding New Modules

1. Create a new class extending `Module`:

```java
public class MyModule extends Module {
    public MyModule() {
        super("MyModule", "Description", "Category");
    }
    
    @Override
    public void onEnable() {
        // Code when enabled
    }
    
    @Override
    public void onDisable() {
        // Code when disabled
    }
    
    @Override
    public void onTick() {
        // Called every tick
    }
}
```

2. Register in `ModuleRegistry.initializeModules()`:

```java
registerModule(new MyModule());
```

### Changing Colors

Edit color constants in:
- `ClickGUIScreen.java`
- `SearchBar.java`
- `CategoryPanel.java`
- `ModuleButton.java`
- `HUDRenderer.java`

Example:
```java
private static final int BACKGROUND_COLOR = 0xFF1a1a1a; // ARGB format
```

## API Reference

### Module Base Class

```java
Module {
    void onEnable()              // Called when module is enabled
    void onDisable()             // Called when module is disabled
    void onTick()                // Called every client tick
    void toggle()                // Toggle on/off
    void enable()                // Enable module
    void disable()               // Disable module
    boolean isEnabled()          // Check if enabled
    String getName()             // Get module name
    String getDescription()      // Get module description
    String getCategory()         // Get module category
}
```

### Animation Utilities

```java
AnimationUtils {
    float lerp(float start, float end, float progress)
    float easeOutCubic(float progress)
    float easeInCubic(float progress)
    float easeInOutCubic(float progress)
    float easeOutQuad(float progress)
    float easeInQuad(float progress)
    float smoothstep(float progress)
}
```

### Render Utilities

```java
RenderUtils {
    void drawRect(DrawContext context, int x, int y, int width, int height, int color)
    void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color)
    void drawRoundedRectBorder(DrawContext context, int x, int y, int width, int height, int radius, int thickness, int color)
    void drawShadow(DrawContext context, int x, int y, int width, int height, int blur)
    void drawGradient(DrawContext context, int x, int y, int width, int height, int colorStart, int colorEnd)
    int interpolateColor(int color1, int color2, float progress)
}
```

## Keybinds

Keybinds can be customized in Minecraft's Controls settings:
- Search for "MyClient" category
- Rebind "Open GUI" key as needed

## Performance

- Optimized rendering with minimal overdraw
- Efficient animation calculations
- Smooth 60+ FPS performance
- Low memory footprint

## Compatibility

- ✅ Minecraft 1.21.1
- ✅ Fabric Loader 0.16.5+
- ✅ Java 21+
- ✅ All screen resolutions
- ✅ Client-side only (safe for multiplayer)

## Known Limitations

- Module functionality is not implemented (framework only)
- Does not work on servers with NoCheat+ or similar anti-cheat
- Texture rendering requires Minecraft's built-in fonts

## Contributing

Feel free to fork and submit pull requests for improvements!

## License

MIT License - See LICENSE file for details

## Credits

- Built with Fabric Loader
- Modern Minecraft 1.21.1 API
- Community PvP Client Design Inspiration

---

**Author:** rendivaidevis  
**Repository:** [minecraft-clickgui-fabric](https://github.com/rendivaidevis-rgb/minecraft-clickgui-fabric)  
**Mod Version:** 1.0.0  
**Minecraft Version:** 1.21.1
