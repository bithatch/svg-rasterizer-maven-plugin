package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_RASTERIZE_DIRECTORY;


@Mojo(name = MOJO_NAME_RASTERIZE_DIRECTORY, defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class RasterizeDirectoryMojo extends AbstractRasterizeDirectoryMojo {
    @Parameter
    private Output defaults;

    RasterizeDirectoryMojo() {
        super();
    }

    @Override
    public Output createDefaults() {
        return defaults;
    }
}
