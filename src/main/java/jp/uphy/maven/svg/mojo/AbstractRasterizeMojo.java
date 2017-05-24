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


import jp.uphy.maven.svg.model.OutputDefinition;
import jp.uphy.maven.svg.model.Rasterizer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;


/**
 * @author Yuhi Ishikura
 */
abstract class AbstractRasterizeMojo extends AbstractMojo implements Rasterizer.Methods<AbstractOutput> {
    @Parameter(required = true)
    private File destDir;

    @Parameter
    private List<OutputDefinition<AbstractOutput>> outputs;


    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        validate(outputs);
        try {
            new Rasterizer<AbstractOutput>(this, destDir, getLog()).rasterize(createInputs(), outputs);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }

    protected void validate(Collection outputs) throws MojoFailureException {
        if (destDir == null) {
            failure("''{0}'' is not specified.", "destDir");
        }
        if (destDir.exists() && !destDir.isDirectory()) {
            failure("''{0}'' exists but is no directory", "destDir");
        }
    }

    protected abstract List<File> createInputs();

    protected static void failure(String pattern, Object... arguments) throws MojoFailureException {
        throw new MojoFailureException(MessageFormat.format(pattern, arguments));
    }
}
