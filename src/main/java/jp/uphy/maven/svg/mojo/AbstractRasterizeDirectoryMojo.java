package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public abstract class AbstractRasterizeDirectoryMojo extends AbstractRasterizeMojo {
    @Parameter(required = true)
    private File inputDir;

    protected AbstractRasterizeDirectoryMojo() {
        super();
    }

    @Override
    protected void validate(Collection outputs) throws MojoFailureException {
        super.validate(outputs);
        if (inputDir == null || !inputDir.exists() || !inputDir.isDirectory()) {
            failure("''{0}'' does not exist or is no directory", "inputDir");
        }
    }

    @Override
    protected List<File> createInputs() {
        File[] files = inputDir.listFiles();
        return (files != null)
                ? Arrays.asList(files)
                : Collections.<File>emptyList();
    }
}
