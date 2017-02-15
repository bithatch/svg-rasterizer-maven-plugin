package jp.uphy.maven.svg.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collection;

import static java.util.Collections.singletonList;
import static jp.uphy.maven.svg.mojo.Constants.DEFAULT_OUTPUT_FORMAT;
import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_RASTERIZE;


@Mojo(name = MOJO_NAME_RASTERIZE, defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class RasterizeMojo extends AbstractRasterizeMojo<Output> {
    @Parameter
    private File destDir;

    @Override
    Collection<Rasterization> createRasterizations(File inFile, Output output) throws MojoFailureException {
        ensureDestDirIsDirectory();
        return singletonList(
                Rasterization.create(
                        inFile,
                        determineOutFile(inFile, output),
                        output.width, output.height,
                        output.format));
    }

    private void ensureDestDirIsDirectory() throws MojoFailureException {
        if (destDir != null && destDir.exists() && !destDir.isDirectory()) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' is not a directory!", "destDir"));
        }
    }

    private File determineOutFile(File inFile, Output output) throws MojoFailureException {
        if (output.path != null) {
            return output.path;
        }

        if (destDir == null) {
            throw new MojoFailureException(MessageFormat.format("neither ''{0}'' nor ''{1}'' is set!", "destDir", "output.path"));
        }

        String ext = (output.format != null) ? output.format : DEFAULT_OUTPUT_FORMAT;
        return new File(destDir, getFilenameOf(inFile) + "-" + output.width + "x" + output.height + "." + ext);
    }
}
