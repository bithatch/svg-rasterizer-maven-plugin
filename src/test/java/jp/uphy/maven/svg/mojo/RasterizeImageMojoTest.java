package jp.uphy.maven.svg.mojo;


import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_RASTERIZE_IMAGE;

public class RasterizeImageMojoTest extends AbstractRasterizeMojoTest {
    public void testRasterizeImage() throws Exception {
        executeMojo("rasterize-image.xml", MOJO_NAME_RASTERIZE_IMAGE, RasterizeImageMojo.class);

        assertFilesExist(
                "image1-48x48.png",
                "image1-32x32.jpg",
                "image1.png",
                "myimage.png"
        );
    }

    public void testRasterizeImageWithDefaults() throws Exception {
        executeMojo("rasterize-image-with-defaults.xml", MOJO_NAME_RASTERIZE_IMAGE, RasterizeImageMojo.class);

        assertFilesExist(
                "image1+48x48.png",
                "image1+32x32.jpg",
                "image1.png",
                "myimage.png"
        );
    }
}
