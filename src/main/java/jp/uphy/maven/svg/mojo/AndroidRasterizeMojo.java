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

import jp.uphy.maven.svg.model.AndroidScreenResolution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_RASTERIZE_ANDROID;


@Mojo(name = MOJO_NAME_RASTERIZE_ANDROID, defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class AndroidRasterizeMojo extends AbstractRasterizeMojo<AndroidOutput> {
    @Parameter
    private File resDirectory;

    @Override
    Collection<Rasterization> createRasterizations(File inFile, AndroidOutput output) throws MojoFailureException {
        assertResDirectoryIsDirectory();

        Collection<Rasterization> rasterizations = new ArrayList<Rasterization>(AndroidScreenResolution.values().length);
        for (String resolution : output.resolutions) {
            AndroidScreenResolution res = AndroidScreenResolution.valueOf(resolution);
            rasterizations.add(
                    Rasterization.create(
                            inFile, determineResDirectory(output), getFilenameOf(inFile),
                            output.width, output.height, res,
                            output.format));
        }

        return rasterizations;
    }

    private void assertResDirectoryIsDirectory() throws MojoFailureException {
        if (resDirectory != null && !resDirectory.isDirectory()) {
            throw new MojoFailureException(MessageFormat.format("''{0}'' is not a directory!", "resDirectory"));
        }
    }

    private File determineResDirectory(AndroidOutput output) throws MojoFailureException {
        if (output.resDirectory != null) {
            return output.resDirectory;
        }

        if (resDirectory == null) {
            throw new MojoFailureException(MessageFormat.format("neither ''{0}'' nor ''{1}'' is set!", "resDirectory", "output.resDirectory"));
        }

        return resDirectory;
    }
}
