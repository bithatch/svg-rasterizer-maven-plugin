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


/**
 * @author Yuhi Ishikura
 */
abstract class AbstractRasterizeMojo extends AbstractMojo {
    private final SvgTool svgTool;

    @Parameter(required = true)
    private File destDir;

    @Parameter
    private List<Output> outputs;


    AbstractRasterizeMojo() {
        this.svgTool = new SvgTool();
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        validate();
        rasterize(createRasterizations(createInputs()));
    }

    protected void validate() throws MojoFailureException {
        if (destDir == null) {
            failure("''{0}'' is not specified.", "destDir");
        }
        if (destDir.exists() && !destDir.isDirectory()) {
            failure("''{0}'' exists but is no directory", "destDir");
        }
    }

    private Collection<Rasterization> createRasterizations(List<File> inputs) throws MojoFailureException {
        List<Rasterization> rasterizations = new ArrayList<Rasterization>(inputs.size());

        System.out.println(outputs);

        for (File inFile : inputs) {
            for (AbstractOutput output : outputs) {
                output.fillWithDefaults(createDefaults());
                rasterizations.addAll(createRasterizations(inFile, destDir, output));
            }
        }

        return rasterizations;
    }

    private Collection<Rasterization> createRasterizations(File inFile, File destDir, AbstractOutput output) throws MojoFailureException {
        Collection<Rasterization> rasterizations = new ArrayList<Rasterization>();

        for (File outFile : output.getOutFiles(destDir, inFile)) {
            rasterizations.add(new Rasterization(inFile, Replacers.replaceAll(outFile, inFile, output), output.getSize(), output.getFormat()));
        }

        return rasterizations;
    }


    protected abstract List<File> createInputs();
    protected abstract AbstractOutput createDefaults();


    private void rasterize(Iterable<Rasterization> rasterizations) throws MojoFailureException, MojoExecutionException {
        Log log = getLog();
        for (Rasterization rasterization : rasterizations) {
            rasterization.execute(svgTool, log);
        }
    }

    static void failure(String pattern, Object... arguments) throws MojoFailureException {
        throw new MojoFailureException(MessageFormat.format(pattern, arguments));
    }

    static String getFilenameOf(File file) {
        final int i = file.getName().lastIndexOf('.');
        return file.getName().substring(0, i);
    }
}
