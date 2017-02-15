package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collection;

import static java.util.Collections.singletonList;
import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_OUTPUT_FORMAT;
import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_PATH_PATTERN;


/**
 * generic definition of rasterized outputs
 */
public class Output extends AbstractOutput {
    @Parameter(defaultValue = DEFAULT_PATH_PATTERN)
    private String path;
    /**
     * Output file format(png,jpg,pdf,tiff).
     */
    @Parameter(defaultValue = DEFAULT_OUTPUT_FORMAT)
    private String format;


    public String getFormat() {
        return format;
    }

    @Override
    public Collection<File> getOutFiles(File destDir, File inFile) throws MojoFailureException {
        ensureValidValues();
        String absolutePath = (new File(path).isAbsolute()) ? path : new File(destDir, path).getAbsolutePath();
        return singletonList(new File(absolutePath));
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
    public String toString() {
        return MessageFormat.format("Output'{'path=''{0}'', size=''{1}x{2}'', format=''{3}'''}'", path, getSize().getWidth(), getSize().getHeight(), getFormat());
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