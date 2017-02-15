package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_ANDROID_RESOLUTIONS;
import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_ANDROID_RES_DIRECTORY;


class AndroidOutput extends Output {
    /**
     * Android "res" directory.
     */
    @Parameter(defaultValue = DEFAULT_ANDROID_RES_DIRECTORY)
    File resDirectory;

    /**
     * Output resolutions.
     * <ul>
     * <li>LDPI</li>
     * <li>MDPI</li>
     * <li>HDPI</li>
     * <li>XHDPI</li>
     * <li>XXHDPI</li>
     * </ul>
     */
    @Parameter(defaultValue = DEFAULT_ANDROID_RESOLUTIONS)
    List<String> resolutions;
}
