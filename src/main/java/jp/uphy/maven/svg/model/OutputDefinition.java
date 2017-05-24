package jp.uphy.maven.svg.model;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;


public interface OutputDefinition<DEFAULTS extends OutputDefinition> {
    Dimension getSize(File outfile);

    String getFormat();

    Collection<File> getOutFiles(File destDir, File inFile) throws IOException;

    float getQuality();

    void fillWithDefaults(DEFAULTS defaults);
}
