package de.enwaffel.pge;

import java.util.HashMap;

public final class Keybinds {

    public static final int PRESSED = 0;
    public static final int RELEASED = 1;

    private static final HashMap<Integer, Keybinding> BINDINGS_BY_KEY = new HashMap<>();
    private static final HashMap<String, Keybinding> BINDINGS_BY_NAME = new HashMap<>();

    private Keybinds() {}

    public static void set(int key, String name) {
        remove(name);

        Keybinding binding = new Keybinding();
        binding.name = name;
        binding.key = key;
        binding.state = RELEASED;
        binding.justPressed = false;
        binding.justReleased = false;

        BINDINGS_BY_KEY.put(key, binding);
        BINDINGS_BY_NAME.put(name, binding);
    }

    public static void remove(String name) {
        if (!BINDINGS_BY_NAME.containsKey(name)) return;
        Keybinding keybinding = BINDINGS_BY_NAME.get(name);

        BINDINGS_BY_NAME.remove(name);
        BINDINGS_BY_KEY.remove(keybinding.key);
    }

    public static boolean isPressed(String name) {
        if (!BINDINGS_BY_NAME.containsKey(name)) return false;
        return BINDINGS_BY_NAME.get(name).state == PRESSED;
    }

    public static boolean isReleased(String name) {
        if (!BINDINGS_BY_NAME.containsKey(name)) return false;
        return BINDINGS_BY_NAME.get(name).state == RELEASED;
    }

    public static boolean justPressed(String name) {
        if (!BINDINGS_BY_NAME.containsKey(name)) return false;
        return BINDINGS_BY_NAME.get(name).justPressed;
    }

    public static boolean justReleased(String name) {
        if (!BINDINGS_BY_NAME.containsKey(name)) return false;
        return BINDINGS_BY_NAME.get(name).justReleased;
    }

    static void onKeyPress(int key) {
        if (!BINDINGS_BY_KEY.containsKey(key)) return;

        Keybinding keybinding = BINDINGS_BY_KEY.get(key);
        keybinding.state = PRESSED;
        keybinding.justPressed = true;

        Game.handleKeybindingPress(keybinding.name);
    }

    static void onKeyRelease(int key) {
        if (!BINDINGS_BY_KEY.containsKey(key)) return;

        Keybinding keybinding = BINDINGS_BY_KEY.get(key);
        keybinding.state = RELEASED;
        keybinding.justReleased = true;

        Game.handleKeybindingRelease(keybinding.name);
    }

    static void update() {
        for (Keybinding keybinding : BINDINGS_BY_NAME.values()) {
            keybinding.justPressed = false;
            keybinding.justReleased = false;
        }
    }

    private static class Keybinding {

        public String name;
        public int key;
        public int state;
        public boolean justPressed;
        public boolean justReleased;

    }

}
