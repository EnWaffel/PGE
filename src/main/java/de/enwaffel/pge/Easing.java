package de.enwaffel.pge;

public final class Easing {

    private Easing() {}

    public static final EasingFunc BACK_INOUT = x -> {
        final float c1 = 1.70158f;
        final float c2 = c1 * 1.525f;

        return x < 0.5f
                ? ((float)Math.pow(2f * x, 2f) * ((c2 + 1f) * 2f * x - c2)) / 2f
                : ((float)Math.pow(2f * x - 2f, 2f) * ((c2 + 1f) * (x * 2f - 2f) + c2) + 2f) / 2f;
    };
    public static final EasingFunc BACK_OUT = x -> {
        final float c1 = 1.70158f;
        final float c3 = c1 + 1f;

        return 1f + c3 * (float)Math.pow(x - 1f, 3f) + c1 * (float)Math.pow(x - 1f, 2f);
    };
    public static final EasingFunc BACK_IN = x -> {
        final float c1 = 1.70158f;
        final float c3 = c1 + 1f;

        return c3 * x * x * x - c1 * x * x;
    };
    public static final EasingFunc ELASTIC_INOUT = x -> {
        final float c5 = (2f * (float)Math.PI) / 4.5f;

        return x == 0f
                ? 0f
                : x == 1f
                ? 1f
                : x < 0.5f
                ? -((float)Math.pow(2f, 20f * x - 10f) * (float)Math.sin((20f * x - 11.125f) * c5)) / 2f
                : ((float)Math.pow(2f, -20f * x + 10f) * (float)Math.sin((20f * x - 11.125f) * c5)) / 2f + 1f;
    };
    public static final EasingFunc ELASTIC_OUT = x -> {
        final float c4 = (2f * (float)Math.PI) / 3f;

        return x == 0f
                ? 0f
                : x == 1f
                ? 1f
                : (float)Math.pow(2f, -10f * x) * (float)Math.sin((x * 10f - 0.75f) * c4) + 1f;
    };
    public static final EasingFunc ELASTIC_IN = x -> {
        final float c4 = (2f * (float)Math.PI) / 3f;

        return x == 0f
                ? 0f
                : x == 1f
                ? 1f
                : -(float)Math.pow(2f, 10f * x - 10f) * (float)Math.sin((x * 10f - 10.75f) * c4);
    };
    public static final EasingFunc BOUNCE_OUT = x -> {
        final float n1 = 7.5625f;
        final float d1 = 2.75f;

        if (x < 1f / d1) {
            return n1 * x * x;
        } else if (x < 2f / d1) {
            return n1 * (x -= 1.5f / d1) * x + 0.75f;
        } else if (x < 2.5f / d1) {
            return n1 * (x -= 2.25f / d1) * x + 0.9375f;
        } else {
            return n1 * (x -= 2.625f / d1) * x + 0.984375f;
        }
    };
    public static final EasingFunc BOUNCE_INOUT = x -> x < 0.5f ? (1f - BOUNCE_OUT.calc(1f - 2f * x)) / 2f : (1f + BOUNCE_OUT.calc(2f * x - 1f)) / 2f;
    public static final EasingFunc BOUNCE_IN = x -> 1f - BOUNCE_OUT.calc(1f - x);
    public static final EasingFunc CIRC_INOUT = x -> x < 0.5f ? (1f - (float)Math.sqrt(1f - (float)Math.pow(2f * x, 2f))) / 2f : ((float)Math.sqrt(1 - (float)Math.pow(-2f * x + 2f, 2f)) + 1f) / 2f;
    public static final EasingFunc CIRC_OUT = x -> (float)Math.sqrt(1f - (float)Math.pow(x - 1f, 2f));
    public static final EasingFunc CIRC_IN = x -> 1 - (float)Math.sqrt(1f - (float)Math.pow(x, 2f));
    public static final EasingFunc EXPO_INOUT = x -> x == 0f ? 0f : x == 1f ? 1f : x < 0.5f ? (float)Math.pow(2f, 20f * x - 10f) / 2f : (2f - (float)Math.pow(2f, -20f * x + 10f)) / 2f;
    public static final EasingFunc EXPO_OUT = x -> x == 1f ? 1f : 1f - (float)Math.pow(2f, -10f * x);
    public static final EasingFunc EXPO_IN = x -> x == 0f ? 0f : (float)Math.pow(2f, 10f * x - 10f);
    public static final EasingFunc SIN_INOUT = x -> -((float)Math.cos((float)Math.PI * x) - 1f) / 2f;
    public static final EasingFunc SIN_OUT = x -> (float)Math.sin((x * (float)Math.PI) / 2f);
    public static final EasingFunc SIN_IN = x -> 1f - (float)Math.cos((x * (float)Math.PI) / 2f);
    public static final EasingFunc QUINT_INOUT = x -> x < 0.5f ? 16f * x * x * x * x * x : 1f - (float)Math.pow(-2f * x + 2f, 5f) / 2f;
    public static final EasingFunc QUINT_OUT = x -> 1 - (float)Math.pow(1f - x, 5f);
    public static final EasingFunc QUINT_IN = x -> x * x * x * x * x;
    public static final EasingFunc QUART_INOUT = x -> x < 0.5f ? 8f * x * x * x * x : 1f - (float)Math.pow(-2f * x + 2f, 4f) / 2f;
    public static final EasingFunc QUART_OUT = x -> 1 - (float)Math.pow(1f - x, 4f);
    public static final EasingFunc QUART_IN = x -> x * x * x * x;
    public static final EasingFunc CUBIC_INOUT = x -> x < 0.5f ? 4f * x * x * x : 1f - (float)Math.pow(-2f * x + 2f, 3f) / 2f;
    public static final EasingFunc CUBIC_OUT = x -> 1f - (float)Math.pow(1f - x, 3f);
    public static final EasingFunc CUBIC_IN = x -> x * x * x;
    public static final EasingFunc QUAD_INOUT = x -> x < 0.5f ? 2f * x * x : 1f - (float)Math.pow(-2f * x + 2f, 2f) / 2f;
    public static final EasingFunc QUAD_OUT = x -> 1f - (1f - x) * (1f - x);
    public static final EasingFunc QUAD_IN = x -> x * x;
    public static final EasingFunc LINEAR = x -> x;

    public interface EasingFunc {
        float calc(float x);
    }

}
