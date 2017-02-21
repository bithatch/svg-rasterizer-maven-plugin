package jp.uphy.maven.svg.mojo.android;


import jp.uphy.maven.svg.mojo.AbstractRasterizeMojoTest;
import jp.uphy.maven.svg.mojo.RasterizedImage;

import static jp.uphy.maven.svg.mojo.android.Constants.MOJO_NAME_RASTERIZE_ANDROID_DIRECTORY;


public class RasterizeAndroidDirectoryMojoTest extends AbstractRasterizeMojoTest {
    public void testRasterizeImage() throws Exception {
        executeMojo("rasterize-android-directory.xml", MOJO_NAME_RASTERIZE_ANDROID_DIRECTORY, RasterizeAndroidDirectoryMojo.class);

        assertRasterizedImages(
                new RasterizedImage("res/drawable-ldpi/image1.png", 36, 36, "png"),
                new RasterizedImage("res/drawable-mdpi/image1.png", 48, 48, "png"),
                new RasterizedImage("res/drawable-hdpi/image1.png", 72, 72, "png"),
                new RasterizedImage("res/drawable-xhdpi/image1.png", 96, 96, "png"),
                new RasterizedImage("res/drawable-xxhdpi/image1.png", 144, 144, "png"),
                new RasterizedImage("res/drawable-ldpi/image2.png", 36, 36, "png"),
                new RasterizedImage("res/drawable-mdpi/image2.png", 48, 48, "png"),
                new RasterizedImage("res/drawable-hdpi/image2.png", 72, 72, "png"),
                new RasterizedImage("res/drawable-xhdpi/image2.png", 96, 96, "png"),
                new RasterizedImage("res/drawable-xxhdpi/image2.png", 144, 144, "png"));
    }

    public void testRasterizeImageWithDefaults() throws Exception {
        executeMojo("rasterize-android-directory-with-defaults.xml", MOJO_NAME_RASTERIZE_ANDROID_DIRECTORY, RasterizeAndroidDirectoryMojo.class);

        assertRasterizedImages(
                new RasterizedImage("argel/drawable-ldpi/image1-36x36.png", 36, 36, "png"),
                new RasterizedImage("argel/drawable-mdpi/image1-48x48.png", 48, 48, "png"),
                new RasterizedImage("argel/drawable-ldpi/image2-36x36.png", 36, 36, "png"),
                new RasterizedImage("argel/drawable-mdpi/image2-48x48.png", 48, 48, "png"));
    }

    public void testRasterizeImageWithOutputsFromFiles() throws Exception {
        executeMojo("rasterize-android-directory-outputs-from-files.xml", MOJO_NAME_RASTERIZE_ANDROID_DIRECTORY, RasterizeAndroidDirectoryMojo.class);

        assertRasterizedImages(
                new RasterizedImage("res/drawable-ldpi/image1-36x36.png", 36, 36, "png"),
                new RasterizedImage("res/drawable-mdpi/image1-48x48.png", 48, 48, "png"),
                new RasterizedImage("res/drawable-ldpi/image2-24x24.png", 24, 24, "png"),
                new RasterizedImage("res/drawable-mdpi/image2-32x32.png", 32, 32, "png"));
    }

}
