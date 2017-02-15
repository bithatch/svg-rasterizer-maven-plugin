package jp.uphy.maven.svg.mojo.android;


import jp.uphy.maven.svg.model.AndroidScreenResolution;
import jp.uphy.maven.svg.mojo.AbstractOutput;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jp.uphy.maven.svg.mojo.android.Constants.DEFAULT_ANDROID_RESOLUTIONS;
import static jp.uphy.maven.svg.mojo.android.Constants.DEFAULT_ANDROID_RES_DIR;
import static jp.uphy.maven.svg.mojo.android.Constants.DEFAULT_NAME_PATTERN;
import static jp.uphy.maven.svg.mojo.android.Constants.OUTPUT_FORMAT;


public class Output extends AbstractOutput {
    @Parameter(defaultValue = DEFAULT_ANDROID_RES_DIR)
    private String resDir;

    @Parameter(defaultValue = DEFAULT_NAME_PATTERN)
    private String name;

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
    private List<String> resolutions;

    @Override
    protected String getFormat() {
        return OUTPUT_FORMAT;
    }

    protected Collection<File> getOutFiles(File destDir, File inFile) throws MojoFailureException {
        ensureValidValues();

        Collection<File> outFiles = new ArrayList<File>(resolutions.size());
        for (String res : resolutions) {
            AndroidScreenResolution resolution = AndroidScreenResolution.valueOf(res);
            File drawableDirectory = new File(new File(destDir, resDir), jp.uphy.maven.svg.mojo.android.Constants.DRAWABLE_OUTPUT_PREFIX + resolution.name().toLowerCase()); //$NON-NLS-1$
            outFiles.add(new File(drawableDirectory, name + "." + getFormat()));
        }

        return outFiles;
    }

    private void ensureValidValues() {
        if (name == null) {
            name = DEFAULT_NAME_PATTERN;
        }

        if (resDir == null) {
            resDir =  DEFAULT_ANDROID_RES_DIR;
        }

        if (resolutions == null) {
            resolutions = AndroidScreenResolution.names();
        }
    }

    @Override
    protected void fillWithDefaults(AbstractOutput defaults) {
        super.fillWithDefaults(defaults);
        if (defaults != null && getClass().isInstance(defaults)) {
            if (name == null) {
                name = ((Output) defaults).name;
            }

            if (resDir == null) {
                resDir = ((Output) defaults).resDir;
            }

            if (resolutions == null) {
                resolutions = ((Output) defaults).resolutions;
            }
        }
    }
}
