package jp.uphy.maven.svg.mojo;


import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_RASTERIZE;


public class RasterizeMojoTest extends AbstractMojoTestCase {
    private static final String RESOURCE_DIR = "src/test/resources/jp/uphy/maven/svg/mojo/";
    private static final String OUTPUT_DIR = "target/images/";

    public void testRasterizeSingleFile() throws Exception {
        File pom = getTestFile( RESOURCE_DIR + "rasterize-single.xml" );
        assertNotNull(pom);
        assertTrue(pom.exists());

        RasterizeMojo mojo = (RasterizeMojo) lookupMojo(MOJO_NAME_RASTERIZE, pom);
        assertNotNull(mojo);
        mojo.execute();

        assertFileExists("image1-48x48.png");
        assertFileExists("image1-32x32.jpg");
        assertFileExists("myimage.png");
    }

    private void assertFileExists(String fileName) {
        File file = new File(OUTPUT_DIR, fileName);
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }
}
