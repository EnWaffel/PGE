package de.enwaffel.pge.utils;

public record Range<T extends Number>(T min, T max) {

    public boolean isZero() {
        return min.doubleValue() == 0d && max.doubleValue() == 0d;
    }

}
