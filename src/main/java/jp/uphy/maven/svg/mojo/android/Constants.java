package jp.uphy.maven.svg.mojo.android;


import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_PREFIX;


final class Constants {
    static final String MOJO_NAME_RASTERIZE_ANDROID_IMAGE = MOJO_NAME_PREFIX + "-android-image";
    static final String MOJO_NAME_RASTERIZE_ANDROID_DIRECTORY = MOJO_NAME_PREFIX + "-android-directory";
    static final String DRAWABLE_OUTPUT_PREFIX = "drawable-";
    static final String DEFAULT_ANDROID_RESOLUTIONS = "LDPI,MDPI,HDPI,XHDPI,XXHDPI";
    static final String DEFAULT_ANDROID_RES_DIR = "res";
    static final String DEFAULT_NAME_PATTERN = "{name}";
    static final String OUTPUT_FORMAT = "png";

    private Constants() { /* NO INSTANCES ALLWOED */ }
}
