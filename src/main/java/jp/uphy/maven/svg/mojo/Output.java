package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugins.annotations.Parameter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static java.util.Collections.singletonList;
import static jp.uphy.maven.svg.model.Rasterizer.DEFAULT_OUTPUT_FORMAT;
import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_PATH_PATTERN;


public class Output extends AbstractOutput<AbstractOutput> {
    @Parameter(defaultValue = DEFAULT_PATH_PATTERN)
    private String path;
    /**
     * Output file format(png,jpg,pdf,tiff).
     */
    @Parameter(defaultValue = DEFAULT_OUTPUT_FORMAT)
    private String format;

    public Output() {
        this(null, 0, 0);
    }

    Output(String path, int width, int height) {
        super(width, height);
        this.path = path;
    }

    @Override
    public Dimension getSize(File outfile) {
        return new Dimension(width, height);
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public Collection<File> getOutFiles(File destDir, File inFile) throws IOException {
        ensureValidValues();
        String absolutePath = (new File(path).isAbsolute()) ? path : new File(destDir, path).getAbsolutePath();
        return singletonList(new File(absolutePath));
    }

    String getPath() {
        ensureValidValues();
        return path;
    }

    private void ensureValidValues() {
        if (path == null) {
            path =  DEFAULT_PATH_PATTERN;
        }

        if (format == null) {
            format = DEFAULT_OUTPUT_FORMAT;
        }
    }

    @Override
    public void fillWithDefaults(AbstractOutput defaults) {
        super.fillWithDefaults(defaults);
        if (defaults != null && getClass().isInstance(defaults)) {
            if (path == null) {
                path = ((Output) defaults).path;
            }

            if (format == null) {
                format = defaults.getFormat();
            }
        }
    }
}
