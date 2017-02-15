package jp.uphy.maven.svg.mojo.android;


import jp.uphy.maven.svg.mojo.AbstractRasterizeMojoTest;

import static jp.uphy.maven.svg.mojo.android.Constants.MOJO_NAME_RASTERIZE_ANDROID_IMAGE;


public class RasterizeAndroidImageMojoTest extends AbstractRasterizeMojoTest {
    public void testRasterizeImage() throws Exception {
        executeMojo("rasterize-android-image.xml", MOJO_NAME_RASTERIZE_ANDROID_IMAGE, RasterizeAndroidImageMojo.class);

        assertFilesExist(
                "res/drawable-ldpi/image1.png",
                "res/drawable-mdpi/image1.png",
                "res/drawable-hdpi/image1.png",
                "res/drawable-xhdpi/image1.png",
                "res/drawable-xxhdpi/image1.png",
                "res/drawable-ldpi/image1-32x32.png",
                "argelbargel/drawable-mdpi/image1.png",
                "argelbargel/drawable-hdpi/image1.png",
                "argelbargel/drawable-xhdpi/image1.png",
                "argelbargel/drawable-xxhdpi/image1.png"
        );
    }

    public void testRasterizeImageWithDefaults() throws Exception {
        executeMojo("rasterize-android-image-with-defaults.xml", MOJO_NAME_RASTERIZE_ANDROID_IMAGE, RasterizeAndroidImageMojo.class);

        assertFilesExist(
                "argel/drawable-ldpi/image1.png",
                "argel/drawable-mdpi/image1.png",
                "argel/drawable-ldpi/image1-32x32.png",
                "res/drawable-ldpi/image1.png",
                "res/drawable-mdpi/image1.png"
        );
    }
}
