package de.enwaffel.pge.particle.particles;

import de.enwaffel.pge.Color;
import de.enwaffel.pge.DrawContext;
import de.enwaffel.pge.particle.Particle;
import de.enwaffel.pge.particle.ParticleTemplate;
import de.enwaffel.pge.utils.Properties;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleParticle extends Particle {

    protected final List<Color> colors;
    protected final List<Float> scales;
    protected final int blendMode;

    protected SimpleParticle(ParticleTemplate template, Properties properties) {
        super(template);

        this.scales = properties.get("scales", List.class, new ArrayList<>(List.of(1.0f)));

        if (properties.has("color")) {
            this.colors = new ArrayList<>(List.of(
                    properties.get("random_color", Boolean.class, false) ?
                            Color.random()
                            :
                            properties.get("color", Color.class, new Color())
            ));
        } else {
            this.colors = properties.get("colors", List.class, new ArrayList<Color>());
        }
        blendMode = properties.get("blend_mode", Integer.class, PConstants.BLEND);
    }

    protected void applyTransforms(PGraphics pg, PVector size) {
        pg.translate(pos.x, pos.y);
        pg.translate(size.x * origin.x, size.y * origin.y);
        pg.rotate(rotation);
        pg.translate(-(size.x * origin.x), -(size.y * origin.y));
    }

    @Override
    public void draw(DrawContext ctx) {}

    protected void applyColor(PGraphics pg) {
        Color fillColor = getCurrentColor();
        fillColor.a *= alpha;
        fillColor.applyFill(pg);
    }

    protected void applyEffects(PGraphics pg) {
        pg.blendMode(blendMode);
        pg.scale(getCurrentScale());
    }

    protected Color getCurrentColor() {
        if (colors.isEmpty()) return new Color(255f);
        if (colors.size() == 1) return colors.get(0);

        float t = PApplet.constrain(normalizedLifetime(), 0f, 1f);

        float scaledT = t * (colors.size() - 1);
        int index = (int) scaledT;
        int nextIndex = Math.min(index + 1, colors.size() - 1);
        float localT = scaledT - index;

        Color from = colors.get(index);
        Color to = colors.get(nextIndex);

        return lerpColor(from, to, localT);
    }

    protected float getCurrentScale() {
        if (scales.isEmpty()) return 1f;
        if (scales.size() == 1) return scales.get(0);

        float t = PApplet.constrain(normalizedLifetime(), 0f, 1f);

        float scaledT = t * (scales.size() - 1);
        int index = (int) scaledT;
        int nextIndex = Math.min(index + 1, scales.size() - 1);
        float localT = scaledT - index;

        float from = scales.get(index);
        float to = scales.get(nextIndex);

        return PApplet.lerp(from, to, localT);
    }

    private Color lerpColor(Color a, Color b, float t) {
        return new Color(
                PApplet.lerp(a.r, b.r, t),
                PApplet.lerp(a.g, b.g, t),
                PApplet.lerp(a.b, b.b, t),
                PApplet.lerp(a.a, b.a, t)
        );
    }

}
