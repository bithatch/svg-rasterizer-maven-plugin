package jp.uphy.maven.svg.mojo;


import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public abstract class AbstractRasterizeMojoTest extends AbstractMojoTestCase {
    private static final String RESOURCE_DIR = "src/test/resources/jp/uphy/maven/svg/mojo/";
    private static final String OUTPUT_DIR = "target/test-images/";

    @Override
    public void setUp() throws Exception {
        FileUtils.deleteDirectory(new File(OUTPUT_DIR));
        super.setUp();
    }

    protected void executeMojo(String pomPath, String name, Class<? extends Mojo> mojoClass) throws Exception {
        File pom = getTestFile(RESOURCE_DIR + pomPath);
        assertNotNull(pom);
        assertTrue(pom.exists());

        Mojo mojo = lookupMojo(name, pom);
        assertNotNull(mojo);
        assertTrue(mojoClass.isInstance(mojo));
        mojo.execute();
    }

    protected void assertRasterizedImages(RasterizedImage... images) throws IOException {
        for (RasterizedImage image : images) {
            File file = new File(OUTPUT_DIR, image.path());
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            assertTrue(image.path() + " does not exist", file.exists());
            assertEquals("image " + file + " is not " + image.width() + " wide", image.width(), icon.getIconWidth());
            assertEquals("image " + file + " is not " + image.height() + " high", image.height(), icon.getIconHeight());

            ImageInputStream in = ImageIO.createImageInputStream(file);
            try {
                Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(in);
                assertTrue(imageReaders.hasNext());
                assertEquals("image " + file + " is not of format " + image.format(), image.format().toLowerCase(), imageReaders.next().getFormatName().toLowerCase());
            } finally {
                in.close();
            }
        }
    }
}
