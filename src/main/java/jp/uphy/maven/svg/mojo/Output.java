package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.text.MessageFormat;

import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_OUTPUT_FORMAT;


/**
 * generic definition of rasterized outputs
 */
public class Output {
    @Parameter(required = true)
    int width;

    @Parameter(required = true)
    int height;

    @Parameter
    File path;

    /**
     * Output file format(png,jpg,pdf,tiff).
     */
    @Parameter(defaultValue = DEFAULT_OUTPUT_FORMAT)
    String format;

    @Override
    public String toString() {
        return MessageFormat.format("Output'{'path=''{0}'', size=''{1}x{2}'', format=''{3}'''}'", path, width, height, format);
    }
}
