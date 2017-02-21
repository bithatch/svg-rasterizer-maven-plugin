package jp.uphy.maven.svg.mojo.android;


import jp.uphy.maven.svg.mojo.AbstractRasterizeMojoTest;
import jp.uphy.maven.svg.mojo.RasterizedImage;

import static jp.uphy.maven.svg.mojo.android.Constants.MOJO_NAME_RASTERIZE_ANDROID_IMAGE;


public class RasterizeAndroidImageMojoTest extends AbstractRasterizeMojoTest {
    public void testRasterizeImage() throws Exception {
        executeMojo("rasterize-android-image.xml", MOJO_NAME_RASTERIZE_ANDROID_IMAGE, RasterizeAndroidImageMojo.class);

        assertRasterizedImages(
                new RasterizedImage("res/drawable-ldpi/image1.png", 36, 36, "png"),
                new RasterizedImage("res/drawable-mdpi/image1.png", 48, 48, "png"),
                new RasterizedImage("res/drawable-hdpi/image1.png", 72, 72, "png"),
                new RasterizedImage("res/drawable-xhdpi/image1.png", 96, 96, "png"),
                new RasterizedImage("res/drawable-xxhdpi/image1.png", 144, 144, "png"));
    }

    public void testRasterizeImageWithDefaults() throws Exception {
        executeMojo("rasterize-android-image-with-defaults.xml", MOJO_NAME_RASTERIZE_ANDROID_IMAGE, RasterizeAndroidImageMojo.class);

        assertRasterizedImages(
                new RasterizedImage("argel/drawable-ldpi/image1.png", 36, 36, "png"),
                new RasterizedImage("argel/drawable-mdpi/image1.png", 48, 48, "png"));
    }

    public void testRasterizeImageWithOutputsFromFiles() throws Exception {
        executeMojo("rasterize-android-image-output-from-file.xml", MOJO_NAME_RASTERIZE_ANDROID_IMAGE, RasterizeAndroidImageMojo.class);

        assertRasterizedImages(
                new RasterizedImage("res/drawable-ldpi/image1.png", 36, 36, "png"),
                new RasterizedImage("res/drawable-mdpi/image1.png", 48, 48, "png"));
    }

}
