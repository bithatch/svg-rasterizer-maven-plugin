package jp.uphy.maven.svg.mojo;


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

    String path() {
        return path;
    }

    int width() {
        return width;
    }

    int height() {
        return height;
    }


    String format() {
        return format;
    }
}
