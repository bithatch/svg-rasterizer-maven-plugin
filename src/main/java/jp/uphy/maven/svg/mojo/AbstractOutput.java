package jp.uphy.maven.svg.mojo;


import jp.uphy.maven.svg.model.OutputDefinition;
import org.apache.maven.plugins.annotations.Parameter;

import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_QUALITY;


public abstract class AbstractOutput<DEFAULTS extends AbstractOutput> implements OutputDefinition<DEFAULTS> {
    @Parameter(required = true)
    protected int width;

    @Parameter(required = true)
    protected int height;

    @Parameter(defaultValue = DEFAULT_QUALITY)
    private float quality = 0.99f;

    protected AbstractOutput(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public float getQuality() {
        return quality;
    }

    public void fillWithDefaults(AbstractOutput defaults) {
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
