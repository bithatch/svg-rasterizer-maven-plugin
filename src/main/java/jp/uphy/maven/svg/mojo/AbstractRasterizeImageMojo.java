package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

import static java.util.Collections.singletonList;


public abstract class AbstractRasterizeImageMojo extends AbstractRasterizeMojo {
    @Parameter(required = true)
    private File inputFile;

    protected AbstractRasterizeImageMojo() {
        super();
    }

    @Override
    protected void validate() throws MojoFailureException {
        if (inputFile == null || !inputFile.exists() || !inputFile.isFile()) {
            failure("''{0}'' is does not exist or is no file", "inputFile");
        }
    }

    @Override
    protected List<File> createInputs() {
        return singletonList(inputFile);
    }
}
