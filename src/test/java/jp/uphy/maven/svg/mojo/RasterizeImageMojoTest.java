package jp.uphy.maven.svg.mojo;


import org.junit.Ignore;

import static jp.uphy.maven.svg.mojo.Constants.MOJO_NAME_RASTERIZE_IMAGE;

@Ignore
public class RasterizeImageMojoTest extends AbstractRasterizeMojoTest {
    public void testRasterizeImage() throws Exception {
        executeMojo("rasterize-image.xml", MOJO_NAME_RASTERIZE_IMAGE, RasterizeImageMojo.class);

        assertRasterizedImages(
                new RasterizedImage("image1-48x48.png", 48, 48, "png"),
                new RasterizedImage("image1-32x32.jpg", 32, 32, "jpeg"),
                new RasterizedImage("image1.png", 24, 24, "png"),
                new RasterizedImage("myimage.png", 24, 24, "png")
        );
    }

    public void testRasterizeImageWithDefaults() throws Exception {
        executeMojo("rasterize-image-with-defaults.xml", MOJO_NAME_RASTERIZE_IMAGE, RasterizeImageMojo.class);

        assertRasterizedImages(
                new RasterizedImage("image1+48x48.png", 48, 48, "png"),
                new RasterizedImage("image1+32x32.jpg", 32, 32, "jpeg"),
                new RasterizedImage("image1.png", 24, 24, "png"),
                new RasterizedImage("myimage.png", 24, 24, "png")
        );
    }
}
