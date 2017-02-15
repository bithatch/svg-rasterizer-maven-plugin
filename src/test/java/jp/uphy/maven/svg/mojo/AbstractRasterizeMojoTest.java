package jp.uphy.maven.svg.mojo;


import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

import static java.util.Arrays.asList;


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

    protected void assertFilesExist(String... fileNames) throws InterruptedException {
        for (String fileName : fileNames) {
            assertFileExists(fileName);
        }
    }

    private void assertFileExists(String fileName) {
        File file = new File(OUTPUT_DIR, fileName);
        assertTrue(file + " does not exist", file.exists());
        assertTrue(file + " is not a file", file.isFile());
    }
}
