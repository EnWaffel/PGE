package de.enwaffel.pge;

import com.jogamp.newt.opengl.GLWindow;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.nio.file.Path;
import java.util.Random;

public class Game {

    public static final String RENDERER = "de.enwaffel.pge.PGERenderer";

    public static PApplet APP = null;
    public static Scene CUR_SCENE = null;
    public static PVector ORIGIN = new PVector(0.5f, 0.5f);

    static boolean WINDOW_TRANSPARENT = false;

    private static final Random rng = new Random();
    private static int last = 0;
    private static boolean inStep = false;
    private static float windowTransparency = 255f;
    private static boolean cursorLocked = false;
    private static float virtualX, virtualY;

    private Game() {}

    public static void init(PApplet app, String basePath) {
        APP = app;

        Assets.BASE_PATH = Path.of(basePath);

        last = APP.millis();
    }

    public static void changeScene(Scene newScene) {
        try {
            if (CUR_SCENE != null) {
                CUR_SCENE.disable();
                if (inStep) throw new EscapeException();
            }

            CUR_SCENE = newScene;
            CUR_SCENE.enable();
        } catch (Throwable throwable) {
            throwable.printStackTrace(System.err);
            throw throwable;
        }
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
        getNativeWindow().setAlwaysOnTop(alwaysOnTop);
    }

    public static void setAlwaysOnBottom(boolean alwaysOnBottom) {
        getNativeWindow().setAlwaysOnBottom(alwaysOnBottom);
    }

    public static void setWindowTransparency(float transparency) {
        windowTransparency = transparency;
    }

    public static float randomFloat() {
        return rng.nextFloat();
    }

    public static float randomFloat(float max) {
        return rng.nextFloat(max);
    }

    public static float randomFloat(float min, float max) {
        return (float) randomNumber(min, max);
    }

    public static int randomInt() {
        return rng.nextInt();
    }

    public static int randomInt(int max) {
        return rng.nextInt(max);
    }

    public static int randomInt(int min, int max) {
        return (int) Math.round(randomNumber(min, max));
    }

    public static double randomNumber(double min, double max) {
        return min + getRNG().nextDouble() * (max - min);
    }

    public static Random getRNG() {
        return rng;
    }

    public static void setWindowTransparent() {
        WINDOW_TRANSPARENT = true;
        setWindowTransparency(0f);
    }

    public static void setWindowOpaque() {
        WINDOW_TRANSPARENT = false;
        setWindowTransparency(255f);
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

    public static void mouseWheel(MouseEvent event) {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onMouseWheel(event.getCount());
    }

    public static void mouseMoved() {
        if (CUR_SCENE == null) return;

        if (!cursorLocked) {
            CUR_SCENE.onMouseMove(APP.mouseX, APP.mouseY);
            return;
        }

        float dx = APP.mouseX - APP.width / 2f;
        float dy = APP.mouseY - APP.height / 2f;
        if (dx == 0 && dy == 0) return;

        virtualX += dx;
        virtualY += dy;
        CUR_SCENE.onMouseMove(virtualX, virtualY);
        recenterCursor();
    }

    public static void mouseDragged() {
        mouseMoved();
    }

    public static void lockCursor() {
        GLWindow wnd = getNativeWindow();
        virtualX = APP.mouseX;
        virtualY = APP.mouseY;
        cursorLocked = true;
        wnd.setPointerVisible(false);
        wnd.confinePointer(true);
        wnd.requestFocus();
        recenterCursor();
    }

    public static void unlockCursor() {
        cursorLocked = false;
        GLWindow wnd = getNativeWindow();
        wnd.setPointerVisible(true);
        wnd.confinePointer(false);
    }

    private static void recenterCursor() {
        GLWindow wnd = getNativeWindow();
        float scale = wnd.getWidth() / (float) APP.width;
        wnd.warpPointer((int) (APP.width / 2f * scale), (int) (APP.height / 2f * scale));
    }

    public static void setWindowPosition(int x, int y) {
        getNativeWindow().setPosition(x, y);
    }

    static GLWindow getNativeWindow() {
        return (GLWindow) APP.getSurface().getNative();
    }

    static void handleKeybindingPress(String name) {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onKeybindPress(name);
    }

    static void handleKeybindingRelease(String name) {
        if (CUR_SCENE == null) return;
        CUR_SCENE.onKeybindRelease(name);
    }

    static class EscapeException extends RuntimeException { }

}
