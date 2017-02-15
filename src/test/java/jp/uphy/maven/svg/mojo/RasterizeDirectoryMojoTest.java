package jp.uphy.maven.svg.mojo;


import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_RASTERIZE_DIRECTORY;

public class RasterizeDirectoryMojoTest extends AbstractRasterizeMojoTest {
    public void testRasterizeDirectory() throws Exception {
        executeMojo("rasterize-directory.xml", MOJO_NAME_RASTERIZE_DIRECTORY, RasterizeDirectoryMojo.class);

        assertFilesExist(
                "48x48/image1.png",
                "48x48/image2.png",
                "32x32/image1.png",
                "32x32/image2.png",
                "image1-24x24.png",
                "image2-24x24.png");
    }

    public void testRasterizeDirectoryWithDefaults() throws Exception {
        executeMojo("rasterize-directory-with-defaults.xml", MOJO_NAME_RASTERIZE_DIRECTORY, RasterizeDirectoryMojo.class);

        assertFilesExist(
                "48x48/image1.png",
                "48x48/image2.png",
                "32x32/image1.png",
                "32x32/image2.png",
                "image1+24x24.png",
                "image2+24x24.png");
    }
}
