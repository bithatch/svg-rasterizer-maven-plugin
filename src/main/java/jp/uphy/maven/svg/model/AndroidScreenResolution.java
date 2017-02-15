package jp.uphy.maven.svg.model;


import java.util.ArrayList;
import java.util.List;


public enum AndroidScreenResolution {
    LDPI(0.75),
    MDPI(1),
    HDPI(1.5),
    XHDPI(2),
    XXHDPI(3);

    public static List<String> names() {
        List<String> names = new ArrayList<String>();
        for (AndroidScreenResolution r : values()) {
            names.add(r.name());
        }

        return names;
    }

    private final double scale;

    AndroidScreenResolution(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return this.scale;
    }

}