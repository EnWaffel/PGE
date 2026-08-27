package de.enwaffel.pge;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.List;
import java.util.ArrayList;

public abstract class Scene extends ColorConstants implements Drawable, PGEConstants, InputHandler {

    public List<SceneObject> objects = new ArrayList<>();
    public List<Camera> cameras = new ArrayList<>(List.of(new Camera()));
    public Camera defaultCamera = cameras.get(0);

    public int physicsVelocityIterations = 6;
    public int physicsPositionIterations = 2;
    public World world = new World(new Vec2(0f, 9.81f));

    public void add(SceneObject obj) {
        if (obj.getCamera() == null) {
            defaultCamera.add(obj);
        }

        objects.add(obj);
    }

    public void add(SceneObject obj, Camera camera) {
        camera.add(obj);
        objects.add(obj);
    }

    public void remove(SceneObject obj) {
        if (obj.getCamera() != null) {
            obj.getCamera().remove(obj);
        }

        objects.remove(obj);
    }

    public void addCamera(Camera camera) {
        cameras.add(camera);
        objects.addAll(camera.objects);
    }

    public void removeCamera(Camera camera) {
        cameras.remove(camera);
        objects.removeAll(camera.objects);
    }

    public abstract void enable();

    // Derivatives should call this
    public void step(float delta) {
        for (Camera camera : cameras) {
            camera.update(delta);
        }

        for (SceneObject obj : objects) {
            obj.update(delta);
        }

        world.step(delta, physicsVelocityIterations, physicsPositionIterations);
    }

    public abstract void disable();

    public void onKeybindPress(String name) {
        // Do nothing
    }

    public void onKeybindRelease(String name) {
        // Do nothing
    }

    @Override
    public void draw(DrawContext ctx) {
        for (Camera camera : cameras) {
            camera.draw(ctx);
        }
    }

    @Override
    public void onKeyPress(char key) {
        for (SceneObject obj : objects) {
            obj.onKeyPress(key);
        }
    }

    @Override
    public void onKeyRelease(char key) {
        for (SceneObject obj : objects) {
            obj.onKeyRelease(key);
        }
    }

    @Override
    public void onMousePress(int button) {
        for (SceneObject obj : objects) {
            obj.onMousePress(button);
        }
    }

    @Override
    public void onMouseRelease(int button) {
        for (SceneObject obj : objects) {
            obj.onMouseRelease(button);
        }
    }

    @Override
    public void onMouseMove(float x, float y) {
        for (SceneObject obj : objects) {
            obj.onMouseMove(x, y);
        }
    }

    @Override
    public void onMouseWheel(float distance) {
        for (SceneObject obj : objects) {
            obj.onMouseWheel(distance);
        }
    }

}
