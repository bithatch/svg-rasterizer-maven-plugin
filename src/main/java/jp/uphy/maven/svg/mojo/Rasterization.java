package jp.uphy.maven.svg.mojo;


import jp.uphy.maven.svg.model.ImageFormat;
import jp.uphy.maven.svg.model.SvgTool;
import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.awt.*;
import java.io.File;
import java.text.MessageFormat;

import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_OUTPUT_FORMAT;


class Rasterization {
    private File input;
    private File output;
    private Dimension size;
    private String extension;


    Rasterization(File input, File output, Dimension size, String extension) {
        this.input = input;
        this.output = output;
        this.size = size;
        this.extension = (extension != null) ? extension : DEFAULT_OUTPUT_FORMAT;
    }

    void execute(SvgTool svgTool, Log log) throws MojoExecutionException, MojoFailureException {
        try {
            log.info(MessageFormat.format("Rasterizing[{0}]", this));
            createOutputDirectory(output.getParentFile());
            svgTool.rasterize(input, output, size.width, size.height, determineDestinationType(extension));
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
                ", size=" + size.width + "x" + size.height +
                ", extension='" + extension + '\'' +
                '}';
    }
}
