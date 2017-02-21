package jp.uphy.maven.svg.mojo;


import java.util.regex.Pattern;


public class Constants {
    public static final String MOJO_NAME_PREFIX = "rasterize";
    static final String MOJO_NAME_RASTERIZE_IMAGE = MOJO_NAME_PREFIX + "-image";
    static final String MOJO_NAME_RASTERIZE_DIRECTORY = MOJO_NAME_PREFIX + "-directory";
    static final String DEFAULT_OUTPUT_FORMAT = "png";
    static final String DEFAULT_PATH_PATTERN = "{name}-{width}x{height}{ext}";
    static final String DEFAULT_QUALITY = "0.99f";
    static final Pattern OUTPUT_SIZE_PATTERN = Pattern.compile("(\\d+)x(\\d+)");
    static final Pattern OUTPUT_IN_FILE_PATTERN = Pattern.compile("(.+)-\\[((" + OUTPUT_SIZE_PATTERN.pattern() + ",?)+)]");

    private Constants() { /* NO INSTANCES ALLWOED */ }
}
