package jp.uphy.maven.svg.mojo;


import jp.uphy.maven.svg.model.AndroidScreenResolution;
import jp.uphy.maven.svg.model.ImageFormat;
import jp.uphy.maven.svg.model.SvgTool;
import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.text.MessageFormat;

import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_OUTPUT_FORMAT;


class Rasterization {
    static Rasterization create(File input, File output, int width, int height, String format) {
        return new Rasterization(input, output, width, height, format);
    }

    static Rasterization create(File input, File resDir, String outputName, int width, int height, AndroidScreenResolution resolution, String format) {
        double scale = resolution.getScale();
        int resWidth = (int)Math.ceil(width * scale);
        int resHeight = (int)Math.ceil(height * scale);

        File drawableDirectory = new File(resDir, Constants.DRAWABLE_OUTPUT_PREFIX + resolution.name().toLowerCase()); //$NON-NLS-1$
        File output = new File(drawableDirectory, outputName + DEFAULT_OUTPUT_FORMAT); //$NON-NLS-1$

        return create(input, output, resWidth, resHeight, format);
    }


    private File input;
    private File output;
    private int width;
    private int height;
    private String extension;


    private Rasterization(File input, File output, int width, int height, String extension) {
        this.input = input;
        this.output = output;
        this.width = width;
        this.height = height;
        this.extension = extension;
    }

    void execute(SvgTool svgTool, Log log) throws MojoExecutionException, MojoFailureException {
        try {
            log.info(MessageFormat.format("Rasterizing[{0}]", this));
            createOutputDirectory(output.getParentFile());
            svgTool.rasterize(input, output, width, height, determineDestinationType(extension));
        } catch (SVGConverterException e) {
            throw new MojoExecutionException(MessageFormat.format("Failure to rasterize : {0}", output));
        }
    }

    private void createOutputDirectory(File directory) throws MojoFailureException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                throw new MojoFailureException(MessageFormat.format("''{0}'' already exist but is not a directory: {1}", "outputDirectory", directory));
            }
            return;
        }
        if (!directory.mkdirs()) {
            throw new MojoFailureException(MessageFormat.format("Failure to make ''{0}'' directory : ", "outputDirectory", directory));
        }
    }

    private DestinationType determineDestinationType(String extension) throws MojoExecutionException {
        if (StringUtils.isEmpty(extension)) {
            return determineDestinationType(DEFAULT_OUTPUT_FORMAT);
        }

        try {
            return ImageFormat.fromExtension(extension).getDestinationType();
        } catch (IllegalArgumentException ex) {
            throw new MojoExecutionException(ex.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Rasterization{" +
                "input=" + input +
                ", output=" + output +
                ", width=" + width +
                ", height=" + height +
                ", extension='" + extension + '\'' +
                '}';
    }
}
