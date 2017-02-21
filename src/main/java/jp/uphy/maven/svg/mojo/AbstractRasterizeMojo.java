/*
  Copyright (C) 2013 uphy.jp

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package jp.uphy.maven.svg.mojo;


import jp.uphy.maven.svg.model.SvgTool;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;


/**
 * @author Yuhi Ishikura
 */
abstract class AbstractRasterizeMojo extends AbstractMojo {
    private final SvgTool svgTool;

    @Parameter(required = true)
    private File destDir;

    @Parameter
    private List<AbstractOutput> outputs;


    AbstractRasterizeMojo() {
        this.svgTool = new SvgTool();
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        validate(outputs);
        rasterize(createRasterizations(createInputs()));
    }

    protected void validate(Collection outputs) throws MojoFailureException {
        if (destDir == null) {
            failure("''{0}'' is not specified.", "destDir");
        }
        if (destDir.exists() && !destDir.isDirectory()) {
            failure("''{0}'' exists but is no directory", "destDir");
        }
    }

    private Collection<Rasterization> createRasterizations(List<File> inputs) throws MojoFailureException {
        List<Rasterization> rasterizations = new ArrayList<Rasterization>(inputs.size());

        for (File inFile : inputs) {
            for (AbstractOutput output : createOutputs(inFile)) {
                output.fillWithDefaults(createDefaults());
                rasterizations.addAll(createRasterizations(inFile, destDir, output));
            }
        }

        return rasterizations;
    }

    private List<AbstractOutput> createOutputs(File inFile) {
        if (outputs != null && outputs.size() > 0) {
            return outputs;
        }

        return createOutputsFromInputFile(inFile);
    }

    private List<AbstractOutput> createOutputsFromInputFile(File inFile) {
        List<AbstractOutput> outputsFromFile = new ArrayList<AbstractOutput>();

        Matcher fileMatcher = Constants.OUTPUT_IN_FILE_PATTERN.matcher(inFile.getName());
        if (fileMatcher.find()) {
            Matcher outputMatcher = Constants.OUTPUT_SIZE_PATTERN.matcher(fileMatcher.group(2));
            while (outputMatcher.find()) {
                String name = fileMatcher.group(1);
                Integer width = Integer.valueOf(outputMatcher.group(1));
                Integer height = Integer.valueOf(outputMatcher.group(2));
                outputsFromFile.add(createOutput(name, width, height));
            }
        }

        return outputsFromFile;
    }

    private Collection<Rasterization> createRasterizations(File inFile, File destDir, AbstractOutput output) throws MojoFailureException {
        Collection<Rasterization> rasterizations = new ArrayList<Rasterization>();

        for (File outFile : output.getOutFiles(destDir, inFile)) {
            File rasterOutFile = Replacers.replaceAll(outFile, inFile, output);
            rasterizations.add(new Rasterization(inFile, rasterOutFile, output.getSize(outFile), output.getQuality(), output.getFormat()));
        }

        return rasterizations;
    }

    protected abstract List<File> createInputs();
    protected abstract AbstractOutput createDefaults();

    protected abstract AbstractOutput createOutput(String name, int width, int height);

    private void rasterize(Iterable<Rasterization> rasterizations) throws MojoFailureException, MojoExecutionException {
        Log log = getLog();
        for (Rasterization rasterization : rasterizations) {
            rasterization.execute(svgTool, log);
        }
    }

    protected static void failure(String pattern, Object... arguments) throws MojoFailureException {
        throw new MojoFailureException(MessageFormat.format(pattern, arguments));
    }
}
