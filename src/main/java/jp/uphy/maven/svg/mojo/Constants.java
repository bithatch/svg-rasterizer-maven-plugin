package jp.uphy.maven.svg.mojo;


public class Constants {
    public static final String MOJO_NAME_PREFIX = "rasterize";
    static final String MOJO_NAME_RASTERIZE_IMAGE = MOJO_NAME_PREFIX + "-image";
    static final String MOJO_NAME_RASTERIZE_DIRECTORY = MOJO_NAME_PREFIX + "-directory";
    static final String DEFAULT_PATH_PATTERN = "{name}-{width}x{height}{ext}";
    static final String DEFAULT_QUALITY = "0.99f";

    private Constants() { /* NO INSTANCES ALLWOED */ }
}
