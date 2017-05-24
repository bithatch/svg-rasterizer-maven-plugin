package jp.uphy.maven.svg.mojo;


import jp.uphy.maven.svg.model.OutputDefinition;
import jp.uphy.maven.svg.model.Replacers;
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
        if (defaults == null) {
            defaults = new Output();
        }
        return defaults;
    }

    @Override
    public OutputDefinition<AbstractOutput> createOutput(String name, int width, int height) {
        return new Output(Replacers.NAME.replace(createDefaults().getPath(), name), width, height);
    }
}
