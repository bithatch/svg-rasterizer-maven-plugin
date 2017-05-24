package jp.uphy.maven.svg.model;


import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;


class Rasterization {
    private File input;
    private File output;
    private Dimension size;
    private float quality;
    private String extension;


    Rasterization(File inFile, File outFile, Dimension size, float quality, String format) {
        this.input = inFile;
        this.output = outFile;
        this.size = size;
        this.quality = quality;
        this.extension = (format != null) ? format : Rasterizer.DEFAULT_OUTPUT_FORMAT;
    }

    void execute(SvgTool svgTool, Log log) throws IOException, SVGConverterException {
        log.info(MessageFormat.format("Rasterizing[{0}]", this));
        createOutputDirectory(output.getParentFile());
        svgTool.rasterize(input, output, size.width, size.height, quality, determineDestinationType(extension));
    }

    private void createOutputDirectory(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                throw new IOException(MessageFormat.format("''{0}'' already exist but is not a directory: {1}", "outputDirectory", directory));
            }
            return;
        }
        if (!directory.mkdirs()) {
            throw new IOException(MessageFormat.format("Failure to make ''{0}'' directory : ", "outputDirectory", directory));
        }
    }

    private DestinationType determineDestinationType(String extension) throws IllegalArgumentException {
        if (StringUtils.isEmpty(extension)) {
            return determineDestinationType(Rasterizer.DEFAULT_OUTPUT_FORMAT);
        }

        return ImageFormat.fromExtension(extension).getDestinationType();
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
