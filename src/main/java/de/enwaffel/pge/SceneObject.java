package de.enwaffel.pge;

import processing.core.PVector;

public interface SceneObject extends Drawable, Updatable, InputHandler {
    void setCamera(Camera camera);
    Camera getCamera();
    void setVisible(boolean visible);
    boolean isVisible();
    void setPosition(PVector pos);
    PVector getPosition();
}
