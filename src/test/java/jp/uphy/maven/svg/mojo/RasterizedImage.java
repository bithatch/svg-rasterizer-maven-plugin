package jp.uphy.maven.svg.mojo;


import java.awt.*;
import java.io.File;


public class RasterizedImage {
    private final String path;
    private final int width;
    private final int height;
    private final String format;

    public RasterizedImage(String path, int width, int height, String format) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.format = format;
    }

    public String path() {
        return path;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }


    public String format() {
        return format;
    }
}
