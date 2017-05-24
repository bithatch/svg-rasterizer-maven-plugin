package jp.uphy.maven.svg.model;


import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Rasterizer<OUTPUT extends OutputDefinition> {
    public static final String DEFAULT_OUTPUT_FORMAT = "png";
    private static final Pattern OUTPUT_SIZE_PATTERN = Pattern.compile("(\\d+)x(\\d+)");
    private static final Pattern OUTPUT_IN_FILE_PATTERN = Pattern.compile("(.+)-\\[((" + OUTPUT_SIZE_PATTERN.pattern() + ",?)+)]");


    private final SvgTool svgTool;
    private final Methods<OUTPUT> methods;
    private final File destDir;
    private final Log log;


    public Rasterizer(Methods<OUTPUT> methods, File destDir, Log log) {
        this.svgTool = new SvgTool();
        this.methods = methods;
        this.destDir = destDir;
        this.log = log;
    }

    public void rasterize(List<File> inputs, List<OutputDefinition<OUTPUT>> outputs) throws IOException {
        rasterize(createRasterizations(inputs, outputs));
    }

    private void rasterize(Iterable<Rasterization> rasterizations) throws IOException {
        for (Rasterization rasterization : rasterizations) {
            try {
                rasterization.execute(svgTool, log);
            } catch (Exception e) {
                throw new IOException(MessageFormat.format("Failure to rasterize : {0}", rasterization));
            }
        }
    }

    private Collection<Rasterization> createRasterizations(List<File> inputs, List<OutputDefinition<OUTPUT>> outputs) throws IOException {
        List<Rasterization> rasterizations = new ArrayList<Rasterization>(inputs.size());

        for (File inFile : inputs) {
            for (OutputDefinition<OUTPUT> output : createOutputs(inFile, outputs)) {
                output.fillWithDefaults(methods.createDefaults());
                rasterizations.addAll(createRasterizations(inFile, destDir, output));
            }
        }

        return rasterizations;
    }

    private Collection<Rasterization> createRasterizations(File inFile, File destDir, OutputDefinition<?> output) throws IOException {
        Collection<Rasterization> rasterizations = new ArrayList<Rasterization>();

        for (File outFile : output.getOutFiles(destDir, inFile)) {
            File rasterOutFile = Replacers.replaceAll(outFile, inFile, output);
            rasterizations.add(new Rasterization(inFile, rasterOutFile, output.getSize(outFile), output.getQuality(), output.getFormat()));
        }

        return rasterizations;
    }


    private List<OutputDefinition<OUTPUT>> createOutputs(File inFile, List<OutputDefinition<OUTPUT>> outputs) {
        if (outputs != null && outputs.size() > 0) {
            return outputs;
        }

        return createOutputsFromInputFile(inFile);
    }

    private List<OutputDefinition<OUTPUT>> createOutputsFromInputFile(File inFile) {
        List<OutputDefinition<OUTPUT>> outputsFromFile = new ArrayList<OutputDefinition<OUTPUT>>();

        Matcher fileMatcher = OUTPUT_IN_FILE_PATTERN.matcher(inFile.getName());
        if (fileMatcher.find()) {
            Matcher outputMatcher = OUTPUT_SIZE_PATTERN.matcher(fileMatcher.group(2));
            while (outputMatcher.find()) {
                String name = fileMatcher.group(1);
                Integer width = Integer.valueOf(outputMatcher.group(1));
                Integer height = Integer.valueOf(outputMatcher.group(2));
                outputsFromFile.add(methods.createOutput(name, width, height));
            }
        }

        return outputsFromFile;
    }

    public interface Methods<OUTPUT extends OutputDefinition> {
        OUTPUT createDefaults();

        OutputDefinition<OUTPUT> createOutput(String name, int width, int height);
    }

}
