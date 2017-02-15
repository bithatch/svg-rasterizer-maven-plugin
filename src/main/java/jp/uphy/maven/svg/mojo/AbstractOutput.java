package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.awt.*;
import java.io.File;
import java.util.Collection;

import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_QUALITY;


public abstract class AbstractOutput {
    @Parameter(required = true)
    private int width;

    @Parameter(required = true)
    private int height;

    @Parameter(defaultValue = DEFAULT_QUALITY)
    private float quality = 0.99f;


    protected abstract String getFormat();
    protected abstract Collection<File> getOutFiles(File destDir, File inFile) throws MojoFailureException;

    Dimension getSize() {
        return new Dimension(width, height);
    }

    float getQuality() {
        return quality;
    }

    protected void fillWithDefaults(AbstractOutput defaults) {
        if (defaults != null) {
            if (width < 1) {
                width = defaults.getSize().width;
            }

            if (height < 1) {
                height = defaults.getSize().height;
            }

            if (quality < 0.1) {
                quality = defaults.getQuality();
            }
        }
    }

}
