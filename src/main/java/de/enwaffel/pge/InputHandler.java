package de.enwaffel.pge;

public interface InputHandler {
    void onKeyPress(char key);
    void onKeyRelease(char key);
    void onMousePress(int button);
    void onMouseRelease(int button);
    void onMouseMove(float x, float y);
    void onMouseWheel(float distance);
}
