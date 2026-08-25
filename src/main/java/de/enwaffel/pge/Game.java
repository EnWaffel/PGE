package de.enwaffel.pge;

import com.jogamp.newt.opengl.GLWindow;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.nio.file.Path;

public class Game {

    public static final String RENDERER_2D = "de.enwaffel.pge.PGERenderer";

    public static PApplet APP = null;
    public static Scene CUR_SCENE = null;

    private static int last = 0;
    private static boolean inStep = false;
    private static float windowTransparency = 255f;

    private Game() {}

    public static void init(PApplet app, String basePath) {
        APP = app;

        Assets.BASE_PATH = Path.of(basePath);

        last = APP.millis();
    }

    public static void changeScene(Scene newScene) {
        if (CUR_SCENE != null) {
            CUR_SCENE.disable();
            if (inStep) throw new EscapeException();
        }

        CUR_SCENE = newScene;
        CUR_SCENE.enable();
    }

    public static void step() {
        int now = APP.millis();
        float delta = (now - last) / 1000f;

        APP.g.background(0, windowTransparency);
        if (CUR_SCENE != null) {
            try {
                inStep = true;
                CUR_SCENE.step(delta);
                inStep = false;
            } catch (EscapeException ignored) {
                return;
            }

            CUR_SCENE.draw(new DrawContext(APP.g, delta));
        }

        SoundSystem.update(delta);
        Keybinds.update();

        last = now;
    }

    public static SoundSource playSound(String name) {
        return SoundSystem.play(name);
    }

    public static void setWindowDecorated(boolean decorated) {
        getNativeWindow().setUndecorated(!decorated);
    }

    public static void setAlwaysOnTop(boolean alwaysOnTop) {
        getNativeWindow().setAlwaysOnTop(true);
    }

    public static void setAlwaysOnBottom(boolean alwaysOnBottom) {
        getNativeWindow().setAlwaysOnBottom(true);
    }

    public static void setWindowTransparency(float transparency) {
        windowTransparency = transparency;
    }

    public static void keyPressed() {
        if (CUR_SCENE == null) return;
        Keybinds.onKeyPress(APP.keyCode);
        CUR_SCENE.onKeyPress(APP.key);
    }

    public static void keyReleased() {
        if (CUR_SCENE == null) return;
        Keybinds.onKeyRelease(APP.keyCode);
        CUR_SCENE.onKeyRelease(APP.key);
    }

    public static void mousePressed() {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onMousePress(APP.mouseButton);
    }

    public static void mouseReleased() {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onMouseRelease(APP.mouseButton);
    }

    public static void mouseMoved() {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onMouseMove(APP.mouseX, APP.mouseY);
    }

    public static void mouseWheel(MouseEvent event) {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onMouseWheel(event.getCount());
    }

    static void handleKeybindingPress(String name) {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onKeybindingPress(name);
    }

    static void handleKeybindingRelease(String name) {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onKeybindingRelease(name);
    }

    static GLWindow getNativeWindow() {
        return (GLWindow) APP.getSurface().getNative();
    }

    static class EscapeException extends RuntimeException { }

}
