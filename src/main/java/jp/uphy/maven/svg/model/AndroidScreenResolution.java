package jp.uphy.maven.svg.model;


import org.codehaus.plexus.util.StringUtils;


public enum AndroidScreenResolution {
    LDPI(0.75),
    MDPI(1),
    HDPI(1.5),
    XHDPI(2),
    XXHDPI(3);

    private final double scale;

    AndroidScreenResolution(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return this.scale;
    }

}