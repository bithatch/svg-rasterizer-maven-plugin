package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.awt.*;
import java.io.File;
import java.util.Collection;

import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_QUALITY;


public abstract class AbstractOutput {
    @Parameter(required = true)
    protected int width;

    @Parameter(required = true)
    protected int height;

    @Parameter(defaultValue = DEFAULT_QUALITY)
    private float quality = 0.99f;

    protected abstract Dimension getSize(File outfile);
    protected abstract String getFormat();
    protected abstract Collection<File> getOutFiles(File destDir, File inFile) throws MojoFailureException;

    float getQuality() {
        return quality;
    }

    protected void fillWithDefaults(AbstractOutput defaults) {
        if (defaults != null) {
            if (width < 1) {
                width = defaults.width;
            }

            if (height < 1) {
                height = defaults.height;
            }

            if (quality < 0.1) {
                quality = defaults.quality;
            }
        }
    }
}
