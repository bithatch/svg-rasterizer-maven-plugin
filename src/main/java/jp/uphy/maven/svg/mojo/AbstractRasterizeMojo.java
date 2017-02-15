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
public abstract class AbstractRasterizeMojo<OUTPUT extends Output> extends AbstractMojo {
    private final SvgTool svgTool;

    @Parameter(required = true)
    private File input;

    /**
     * Output definitions.
     * <pre>
     * <outputs>
     *     <output>
     *         <path>target</path>
     *         <width>128</width>
     *         <height>128</height>
     *         <format>png</format>
     *     </output>
     * </outputs>
     * </pre>
     */
    @Parameter
    private List<OUTPUT> outputs;


    AbstractRasterizeMojo() {
        this.svgTool = new SvgTool();
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        assertInputExists();
        rasterize(input.isDirectory() ? createRasterizations(input.listFiles()) : createRasterizations(input));
    }

    private void assertInputExists() throws MojoFailureException {
        if (input == null) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' is not specified.", "input"));
        }
        if (!input.exists()) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' doesn''t exist: {1}", "input", input));
        }
    }

    private Collection<Rasterization> createRasterizations(File... inFiles) throws MojoFailureException {
        List<Rasterization> rasterizations = new ArrayList<Rasterization>(inFiles.length);

        for (File inFile : inFiles) {
            for (OUTPUT output : outputs) {
                rasterizations.addAll(createRasterizations(inFile, output));
            }
        }

        return rasterizations;
    }

    private void rasterize(Iterable<Rasterization> rasterizations) throws MojoFailureException, MojoExecutionException {
        Log log = getLog();
        for (Rasterization rasterization : rasterizations) {
            rasterization.execute(svgTool, log);
        }
    }

    static String getFilenameOf(File file) {
        final int i = file.getName().lastIndexOf('.');
        return file.getName().substring(0, i);
    }

    abstract Collection<Rasterization> createRasterizations(File inFile, OUTPUT output) throws MojoFailureException;
}
